package com.diss.react4j.controller;

import com.diss.react4j.model.Group;
import com.diss.react4j.service.GroupService;
import com.diss.react4j.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/api")
public class GroupController {

    private final Logger logger = LoggerFactory.getLogger(GroupController.class);
    private final UserService userService;
    private final GroupService groupService;

    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping("/groups")
    public Collection<Group> getGroups(Principal principal) {
        return groupService.getGroups(principal);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<?> findGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    @PostMapping("/group")
    public ResponseEntity<?> createGroup(@Valid @RequestBody Group group,
                                         @AuthenticationPrincipal OAuth2User principal) throws URISyntaxException {
        return groupService.createGroup(group, principal);
    }

    @PutMapping("/group/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable Long id, @Valid @RequestBody Group group) throws URISyntaxException {
        return groupService.updateGroup(id, group);
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        return groupService.deleteGroup(id);
    }

}
