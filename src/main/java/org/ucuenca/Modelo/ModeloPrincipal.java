package org.ucuenca.Modelo;

import org.ucuenca.Modelo.Citas.CitaDivorcio;
import org.ucuenca.Modelo.Citas.CitaEntrevistaMatrimonio;
import org.ucuenca.Modelo.Citas.GestorCitas;
import org.ucuenca.Modelo.Citas.HorariosYCostos.Precios;
import org.ucuenca.Modelo.Citas.Plantillas.Cita;
import org.ucuenca.Modelo.Documentos.GestorDocumentosLegales;
import org.ucuenca.Modelo.Documentos.Plantillas.DocumentoLegal;
import org.ucuenca.Modelo.Usuarios.Ciudadano;
import org.ucuenca.Modelo.Usuarios.Roles;
import org.ucuenca.Modelo.Usuarios.Usuario;


import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejo Principal del Sistema, se encuentran las bases de datos y gestión de archivos
 * <p>
 * Usa <b>Singleton</b> por lo que se debe acceder a su instancia mediante {@link ModeloPrincipal#getInstancia()}
 * </p>
 */
public class ModeloPrincipal implements Serializable {
    protected Map<String, Ciudadano> ciudadanos;
    protected Map<String, Usuario> usuarios;
    protected Map<String, Cita> citas;
    protected Map<String, DocumentoLegal> documentos;

    public static final Path pathDB = Paths.get("src/main/resources/Data");
    public GestorDocumentosLegales<DocumentoLegal> gestorDocumentos;
    public GestorCitas<Cita> gestorCitas;

    // Instancia única del Singleton
    private static ModeloPrincipal instancia;

    public static Usuario usuario;

    // Constructor privado para evitar instancias externas
    private ModeloPrincipal() {
        ciudadanos = new HashMap<>();
        usuarios = new HashMap<>();
        documentos = new HashMap<>();
        citas = new HashMap<>();
        gestorDocumentos = new GestorDocumentosLegales<>();
        gestorCitas = new GestorCitas<>();

//        llenarCiudadanosTest();
//        llenarUsuariosTest();
//        llenarDocumentosTest();
        recuperarBasesDatos();
        guardarBasesDatos();
    }

    // Acceder a la instancia correspondiente a la sesión activa
    public static ModeloPrincipal getInstancia() {
        if (instancia == null)
            synchronized (ModeloPrincipal.class) {
                if (instancia == null)
                    instancia = new ModeloPrincipal();
            }

        return instancia;
    }

    private void llenarUsuariosTest() {
        HashMap<String, Usuario> users = new HashMap<>();
        users.put("0105911838", new Usuario("0105911838", "power", "kenn.004@hotmail.com", "0123456789", Roles.EMPLEADO));
        users.put("0103382545", new Usuario("0103382545", "power", "usuario@hotmail.com", "0987654321", Roles.ADMIN));
        users.put("0915518005", new Usuario("0915518005", "power", "padre@outlook.com", "099999999", Roles.USUARIO));
        users.put("0106045271", new Usuario("0106045271", "power", "daniel.calleg@ucuenca.edu.ec", "099999999", Roles.ADMIN));
        users.put("1003992292", new Usuario("1003992292", "power", "csalome.balcazar4@ucuenca.edu.ec", "12345678", Roles.ADMIN));
        this.usuarios = users;
    }

    public void llenarCitasTest(Usuario usuario) {
        LocalDateTime fechaPrueba = LocalDateTime.now().plusMonths(1);

        if (usuario.getCitas() == null)
            usuario.setCitas(new ArrayList<>());

        usuario.getCitas().add(
                new CitaDivorcio(
                        fechaPrueba,
                        Precios.DIVORCIO.getPrecio(),
                        ciudadanos.get(usuario.getId()),
                        ciudadanos.get(usuario.getId()),
                        "1"
                )
        );

        usuario.getCitas().add(
                new CitaEntrevistaMatrimonio(
                        fechaPrueba,
                        Precios.DIVORCIO.getPrecio(),
                        ciudadanos.get(usuario.getId()),
                        Dummies.mujer.getCedula().getIdUnico(),
                        "2"
                )
        );

//        Este debería aparecer como ya pasado
        usuario.getCitas().add(
                new CitaEntrevistaMatrimonio(
                        fechaPrueba.minusMonths(2),
                        Precios.DIVORCIO.getPrecio(),
                        ciudadanos.get(usuario.getId()),
                        Dummies.mujer.getCedula().getIdUnico(),
                        "3"
                )
        );

        actualizarDB(usuario);
        for (Cita cita : usuario.getCitas()) {
            actualizarDB(cita);
        }

    }

    private void llenarCiudadanosTest() {
        Dummies.setDummies();
        this.ciudadanos = Dummies.ciudadanosDummies;
    }

    public void llenarDocumentosTest() {
        this.documentos.put(
                Dummies.nacimientoMarido.getIdDocumento(),
                Dummies.nacimientoMarido
        );
        Dummies.nacimientoMarido.setCiudadanoAsociado(Dummies.marido);
        Dummies.marido.getDocumentosAsociados().add(Dummies.nacimientoMarido);
    }


    /**
     * Escribe las estructuras, <i>posiblemente actualizadas</i> {@link #usuarios}, {@link #ciudadanos} y actas
     * en archivos serializables
     * <p>
     * Ubicación {@link #pathDB}
     * </p>
     */
    public boolean guardarBasesDatos() {
        boolean guardado =
                GestorArchivos.guardarEstructura(this.documentos, pathDB.resolve("documentos")) &&
                        GestorArchivos.guardarEstructura(this.ciudadanos, pathDB.resolve("ciudadanos")) &&
                        GestorArchivos.guardarEstructura(this.usuarios, pathDB.resolve("usuarios")) &&
                        GestorArchivos.guardarEstructura(this.citas, pathDB.resolve("citas"));
        return guardado;
    }

    /**
     * Guarda dentro de la correspondiente estructura y actualiza el archivo correspondiente
     */
    public boolean actualizarDB(DocumentoLegal doc) {
        this.documentos.put(doc.getIdDocumento(), doc);
        return GestorArchivos.guardarEstructura(this.documentos, pathDB.resolve("documentos"));
    }

    /**
     * Guarda dentro de la correspondiente estructura y actualiza el archivo correspondiente
     */
    public boolean actualizarDB(Ciudadano ciudadano) {
        this.ciudadanos.put(ciudadano.getCedula().getIdUnico(), ciudadano);
        return GestorArchivos.guardarEstructura(this.ciudadanos, pathDB.resolve("ciudadanos"));
    }

    /**
     * Guarda dentro de la correspondiente estructura y actualiza el archivo correspondiente
     */
    public boolean actualizarDB(Usuario usuario) {
        this.usuarios.put(usuario.getId(), usuario);
        return GestorArchivos.guardarEstructura(this.usuarios, pathDB.resolve("usuarios"));
    }

    /**
     * Guarda dentro de la correspondiente estructura y actualiza el archivo correspondiente
     */
    public boolean actualizarDB(Cita cita) {
        this.citas.put(cita.getIdCita(), cita);
        return GestorArchivos.guardarEstructura(this.citas, pathDB.resolve("citas"));
    }

    /**
     * Llena las estructuras {@link #usuarios},{@link #ciudadanos} con los guardados en {@link #pathDB}
     */
    public boolean recuperarBasesDatos() {
        this.usuarios = (Map<String, Usuario>) GestorArchivos.recuperarMapa(pathDB.resolve("usuarios"));
        this.ciudadanos = (Map<String, Ciudadano>) GestorArchivos.recuperarMapa(pathDB.resolve("ciudadanos"));
        this.documentos = (Map<String, DocumentoLegal>) GestorArchivos.recuperarMapa(pathDB.resolve("documentos"));
        this.citas = (Map<String, Cita>) GestorArchivos.recuperarMapa(pathDB.resolve("citas"));
        return usuarios != null && ciudadanos != null && documentos != null;
    }

    public String getDocumentoId() {
        return gestorDocumentos.generarId(documentos);
    }

    public String getCitaId() {
        return gestorCitas.generarId(citas);
    }

    /*--- Getters y Setters---*/
    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public Map<String, Ciudadano> getCiudadanos() {
        return ciudadanos;
    }


    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Map<String, DocumentoLegal> getDocumentos() {
        return documentos;
    }

    public String getNombreCompleto(String numCedula) {
        StringBuilder nombres = new StringBuilder();
        if (ciudadanos.containsKey(numCedula)) {
            nombres.append(ciudadanos.get(numCedula).getNombres());
            nombres.append(ciudadanos.get(numCedula).getApellidos());
            return String.valueOf(nombres);
        }
        return "Usuario no encontrado";
    }

    public String getRol(String numCedula) {
        if (usuarios.containsKey(numCedula)) {
            return String.valueOf(usuarios.get(numCedula).getRol());
        }
        return "Rol no encontrado";
    }

    public Map<String, Cita> getCitas() {
        return citas;
    }

    public void eliminarUsu(String numCedula) {
        usuarios.remove(numCedula);
    }
}
