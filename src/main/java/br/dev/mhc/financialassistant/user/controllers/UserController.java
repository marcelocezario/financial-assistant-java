package br.dev.mhc.financialassistant.user.controllers;

import br.dev.mhc.financialassistant.common.constants.RouteConstants;
import br.dev.mhc.financialassistant.common.utils.URIUtils;
import br.dev.mhc.financialassistant.user.dtos.UserDTO;
import br.dev.mhc.financialassistant.user.services.interfaces.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = RouteConstants.USERS_ROUTE)
public class UserController {

    private final ICreateUserService createUser;
    private final IUpdateUserService updateUser;
    private final IDeleteUserService deleteUserService;
    private final IFindAllUsersService findAllUsers;
    private final IFindUserByUuidService findUserById;

    public UserController(ICreateUserService createUser, IUpdateUserService updateUser, IDeleteUserService deleteUserService, IFindAllUsersService findAllUsers, IFindUserByUuidService findUserById) {
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.deleteUserService = deleteUserService;
        this.findAllUsers = findAllUsers;
        this.findUserById = findUserById;
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO) {
        userDTO = createUser.create(userDTO);
        return ResponseEntity.created(URIUtils.buildUri(userDTO.getUuid())).body(userDTO);
    }

    @PutMapping(value = "/{uuid}")
    public ResponseEntity<Void> update(@RequestBody @Valid UserDTO userDTO) {
        updateUser.update(userDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        deleteUserService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(findAllUsers.find());
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(findUserById.find(uuid));
    }

}
