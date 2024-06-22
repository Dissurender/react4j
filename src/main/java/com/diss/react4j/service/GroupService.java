package com.diss.react4j.service;

import com.diss.react4j.model.Group;
import com.diss.react4j.model.GroupRepository;
import com.diss.react4j.model.User;
import com.diss.react4j.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Service
public class GroupService {

    private final Logger logger = LoggerFactory.getLogger(GroupService.class);
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Collection<Group> getGroups(Principal principal) {
        logger.debug("getGroups");
        return groupRepository.findAllByUserId(principal.getName());
    }

    public ResponseEntity<?> getGroupById(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()) {
            logger.debug("getGroupById found: {}", group.get().getName());
            return ResponseEntity.ok().body(group);
        }
        logger.debug("getGroupById failed");
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Group> createGroup(Group group, OAuth2User principal) throws URISyntaxException {
        logger.debug("creating group: {}", group.getName());

        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();

        Optional<User> user = userRepository.findById(userId);
        group.setUser(user.orElse(new User(userId, details.get("name").toString(),
                details.get("email").toString())));

        Group result = groupRepository.save(group);

        logger.debug("group created: {}", result.getName());
        return ResponseEntity.created(
                        new URI("/api/group/" + result.getId()))
                .body(result);
    }

    public ResponseEntity<?> updateGroup(Long id, Group group) {
        logger.debug("updating group: {}", group.getName());
        Optional<Group> foundGroup = groupRepository.findById(id);
        if (foundGroup.isPresent()) {
            Group result = groupRepository.save(foundGroup.get());
            logger.debug("group updated: {}", foundGroup.get().getName());
            return ResponseEntity.ok().body(result);
        }
        logger.debug("group not found, updating failed");
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> deleteGroup(Long id) {
        logger.debug("deleting group: {}", id);
        Optional<Group> foundGroup = groupRepository.findById(id);
        if (foundGroup.isPresent()) {
            groupRepository.delete(foundGroup.get());
            logger.debug("group deleted: {}", foundGroup.get().getName());
            return ResponseEntity.noContent().build();
        }
        logger.debug("group not found, delete failed");
        return ResponseEntity.notFound().build();
    }
}
