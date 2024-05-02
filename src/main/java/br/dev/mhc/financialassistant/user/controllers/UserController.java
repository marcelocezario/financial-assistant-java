package br.dev.mhc.financialassistant.user.controllers;

import br.dev.mhc.financialassistant.common.constants.RouteConstants;
import br.dev.mhc.financialassistant.common.utils.URIUtils;
import br.dev.mhc.financialassistant.user.dtos.UserDTO;
import br.dev.mhc.financialassistant.user.services.interfaces.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = RouteConstants.USERS_ROUTE)
public class UserController {

    private final ICreateUserService createUser;
    private final IUpdateUserService updateUser;
    private final IDeleteUserService deleteUserService;
    private final IFindAllUsersService findAllUsers;
    private final IFindUserByIdService findUserById;

    public UserController(ICreateUserService createUser, IUpdateUserService updateUser, IDeleteUserService deleteUserService, IFindAllUsersService findAllUsers, IFindUserByIdService findUserById) {
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.deleteUserService = deleteUserService;
        this.findAllUsers = findAllUsers;
        this.findUserById = findUserById;
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO) {
        userDTO = createUser.create(userDTO);
        return ResponseEntity.created(URIUtils.buildUri(userDTO.getId())).body(userDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody @Valid UserDTO userDTO) {
        updateUser.update(userDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUserService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(findAllUsers.find());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(findUserById.find(id));
    }

}
