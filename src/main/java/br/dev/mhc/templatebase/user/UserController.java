package br.dev.mhc.templatebase.user;

import br.dev.mhc.templatebase.common.utils.URIUtils;
import br.dev.mhc.templatebase.user.dtos.UserDTO;
import br.dev.mhc.templatebase.user.services.interfaces.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public record UserController(
        ICreateUserService createUser,
        IUpdateUserService updateUser,
        IDeleteUserService deleteUserService,
        IFindAllUsersService findAllUsers,
        IFindUserByIdService findUserById
) {

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO) {
        userDTO = createUser.create(userDTO);
        return ResponseEntity.created(URIUtils.buildUri(userDTO.getId())).body(userDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        userDTO.setId(id);
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
