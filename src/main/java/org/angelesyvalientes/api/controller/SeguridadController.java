package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Permiso;
import org.angelesyvalientes.api.persistence.entity.Rol;
import org.angelesyvalientes.api.service.PermisoService;
import org.angelesyvalientes.api.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Tag(name = "Permisos y roles ")
@RestController
@RequestMapping("/api")
public class SeguridadController {

    private final PermisoService permisoService;
    private final RolService rolService;

    @Autowired
    public SeguridadController(PermisoService permisoService, RolService rolService) {
        this.permisoService = permisoService;
        this.rolService = rolService;
    }

    // -----------------------------------------------------------------
    // Controlador para Permisos
    // -----------------------------------------------------------------

    /**
     * Endpoint para crear un nuevo permiso.
     *
     * @param permiso El objeto Permiso a crear en el cuerpo de la petición.
     * @return ResponseEntity con el permiso creado y estado 201 (CREATED),
     * o estado 400 (BAD REQUEST) si la información es inválida.
     */
    @Operation(summary = "Crear permiso ")
    @PostMapping("/permisos")
    public ResponseEntity<Permiso> crearPermiso(@RequestBody Permiso permiso) {
        if (permiso.getCodigoOpcion() == null || permiso.getCodigoOperacion() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Permiso nuevoPermiso = permisoService.save(permiso);
        return new ResponseEntity<>(nuevoPermiso, HttpStatus.CREATED);
    }

    /**
     * Endpoint para obtener todos los permisos.
     *
     * @return ResponseEntity con la lista de todos los permisos y estado 200 (OK).
     */
    @Operation(summary = "Listar permisos ")
    @GetMapping("/permisos")
    public ResponseEntity<List<Permiso>> obtenerTodosPermisos() {
        List<Permiso> permisos = permisoService.getAll();
        return new ResponseEntity<>(permisos, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener un permiso por su ID.
     *
     * @param id El ID del permiso a buscar (pasado como variable de ruta).
     * @return ResponseEntity con el permiso encontrado y estado 200 (OK),
     * o estado 404 (NOT FOUND) si no se encuentra.
     */
    @Operation(summary = "Obtener permiso por ID")
    @GetMapping("/permisos/{id}")
    public ResponseEntity<Permiso> obtenerPermisoPorId(@PathVariable Integer id) {
        Optional<Permiso> permiso = permisoService.getById(id);
        return permiso.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para actualizar un permiso existente.
     *
     * @param id      El ID del permiso a actualizar (pasado como variable de ruta).
     * @param permiso El objeto Permiso con los datos actualizados en el cuerpo de la petición.
     * @return ResponseEntity con el permiso actualizado y estado 200 (OK),
     * o estado 404 (NOT FOUND) si no se encuentra,
     * o estado 400 (BAD REQUEST) si la información es inválida.
     */
    @Operation(summary = "Actualizar permiso ")
    @PutMapping("/permisos/{id}")
    public ResponseEntity<Permiso> actualizarPermiso(@PathVariable Integer id, @RequestBody Permiso permiso) {
        if (!id.equals(permiso.getIdPermiso()) || permiso.getCodigoOpcion() == null || permiso.getCodigoOperacion() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Permiso permisoActualizado = permisoService.update(permiso);
        return permisoActualizado != null ? new ResponseEntity<>(permisoActualizado, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint para eliminar un permiso por su ID.
     *
     * @param id El ID del permiso a eliminar (pasado como variable de ruta).
     * @return ResponseEntity con estado 204 (NO CONTENT) si se eliminó correctamente,
     * o estado 404 (NOT FOUND) si no se encontró.
     */
    @Operation(summary = "Eliminar permisos por ID ")
    @DeleteMapping("/permisos/{id}")
    public ResponseEntity<Void> eliminarPermiso(@PathVariable Integer id) {
        if (permisoService.getById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        permisoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // -----------------------------------------------------------------
    // Controlador para Roles
    // -----------------------------------------------------------------

    /**
     * Endpoint para crear un nuevo rol.
     *
     * @param rol El objeto Rol a crear en el cuerpo de la petición.
     * @return ResponseEntity con el rol creado y estado 201 (CREATED),
     * o estado 400 (BAD REQUEST) si la información es inválida.
     */
    @Operation(summary = "Crear un rol ")
    @PostMapping("/roles")
    public ResponseEntity<Rol> crearRol(@RequestBody Rol rol) {
        if (rol.getNombre() == null || rol.getNombre().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Rol nuevoRol = rolService.save(rol);
        return new ResponseEntity<>(nuevoRol, HttpStatus.CREATED);
    }

    /**
     * Endpoint para obtener todos los roles.
     *
     * @return ResponseEntity con la lista de todos los roles y estado 200 (OK).
     */
    @Operation(summary = "Listar Roles")
    @GetMapping("/roles")
    public ResponseEntity<List<Rol>> obtenerTodosRoles() {
        List<Rol> roles = rolService.getAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener un rol por su ID.
     *
     * @param id El ID del rol a buscar (pasado como variable de ruta).
     * @return ResponseEntity con el rol encontrado y estado 200 (OK),
     * o estado 404 (NOT FOUND) si no se encuentra.
     */
    @Operation(summary = "Obtener rol por ID ")
    @GetMapping("/roles/{id}")
    public ResponseEntity<Rol> obtenerRolPorId(@PathVariable Integer id) {
        Optional<Rol> rol = rolService.getById(id);
        return rol.map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para actualizar un rol existente.
     *
     * @param id  El ID del rol a actualizar (pasado como variable de ruta).
     * @param rol El objeto Rol con los datos actualizados en el cuerpo de la petición.
     * @return ResponseEntity con el rol actualizado y estado 200 (OK),
     * o estado 404 (NOT FOUND) si no se encuentra,
     * o estado 400 (BAD REQUEST) si la información es inválida.
     */
    @Operation(summary = "Actualizar Rol ")
    @PutMapping("/roles/{id}")
    public ResponseEntity<Rol> actualizarRol(@PathVariable Integer id, @RequestBody Rol rol) {
        if (!id.equals(rol.getIdRol()) || rol.getNombre() == null || rol.getNombre().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Rol rolActualizado = rolService.update(rol);
        return rolActualizado != null ? new ResponseEntity<>(rolActualizado, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint para eliminar un rol por su ID.
     *
     * @param id El ID del rol a eliminar (pasado como variable de ruta).
     * @return ResponseEntity con estado 204 (NO CONTENT) si se eliminó correctamente,
     * o estado 404 (NOT FOUND) si no se encontró.
     */
    @Operation(summary = "Eliminar Rol por Id")
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Integer id) {
        if (rolService.getById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        rolService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint para asignar un permiso a un rol.
     *
     * @param rolId     El ID del rol al que se asignará el permiso.
     * @param permisoId El ID del permiso a asignar.
     * @return ResponseEntity con el rol actualizado y estado 200 (OK),
     * o estado 404 (NOT FOUND) si el rol o el permiso no existen.
     */
    @Operation(summary = "Asignar permisos al rol")
    @PostMapping("/roles/{rolId}/permisos/{permisoId}")
    public ResponseEntity<Rol> asignarPermisoARol(@PathVariable Integer rolId, @PathVariable Integer permisoId) {
        Optional<Rol> rolActualizado = rolService.addPermisoToRol(rolId, permisoId);
        return rolActualizado.map(rol -> new ResponseEntity<>(rol, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para remover un permiso de un rol.
     *
     * @param rolId     El ID del rol del que se removerá el permiso.
     * @param permisoId El ID del permiso a remover.
     * @return ResponseEntity con el rol actualizado y estado 200 (OK),
     * o estado 404 (NOT FOUND) si el rol o el permiso no existen.
     */
    @Operation(summary = "Eliminar permiso al rol ")
    @DeleteMapping("/roles/{rolId}/permisos/{permisoId}")
    public ResponseEntity<Rol> removerPermisoDeRol(@PathVariable Integer rolId, @PathVariable Integer permisoId) {
        Optional<Rol> rolActualizado = rolService.removePermisoFromRol(rolId, permisoId);
        return rolActualizado.map(rol -> new ResponseEntity<>(rol, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}