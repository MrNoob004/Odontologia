package org.ucuenca.Modelo;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;

public class GestorArchivos {

    public static String recuperarContenido(String direccion) {
        FileInputStream archivo = null;
        File dir = new File(direccion);

        StringBuilder contenido = new StringBuilder();

        try {
            if (dir.exists()) {
                archivo = new FileInputStream(direccion);
                int c;
                while ((c = archivo.read()) != -1)
                    contenido.append((char) c);
                archivo.close();
                System.out.println("Contenido recuperado con éxito");
                return contenido.toString();
            }
        } catch (Exception e) {
            System.out.println("No se ha podido recuperar el contenido en la dirección '" + direccion + "'");
        }
        return null;
    }

    public static HashMap<?, ?> recuperarMapa(String nombreArchivo) {
        HashMap<?, ?> mapa = null;
        File path = new File(nombreArchivo);
        ObjectInput archivo = null;

        try {
            archivo = new ObjectInputStream(new FileInputStream(path));
            mapa = (HashMap<?, ?>) archivo.readObject();
            archivo.close();

        } catch (Exception e) {
            System.err.println("No se pudo recuperar el archivo '" + nombreArchivo + "'");
        }
        return mapa;
    }

    /*
        public static HashMap<?, ?> recuperarMapa(Path direccion) {
            HashMap<?, ?> mapa = null;
    //        File path = new File(direccion);
            ObjectInput archivo = null;

            try {
                archivo = new ObjectInputStream(new FileInputStream(String.valueOf(direccion)));
                mapa = (HashMap<?, ?>) archivo.readObject();
                archivo.close();

            } catch (Exception e) {
                System.err.println("No se pudo recuperar el archivo '" + direccion + "'");
            }
            return mapa;
        }
    */
    public static HashMap<?, ?> recuperarMapa(Path direccion) {
        HashMap<?, ?> mapa = null;

        try (ObjectInputStream archivo = new ObjectInputStream(new FileInputStream(direccion.toFile()))) {
            mapa = (HashMap<?, ?>) archivo.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("La clase del objeto no coincide con HashMap: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de entrada/salida al leer el archivo '" + direccion + "': " + e.getMessage());
        }

        return mapa;
    }

    public static boolean guardarEstructura(Object estructura, Path path) {
        try {
            ObjectOutputStream archivo = new ObjectOutputStream(new FileOutputStream(String.valueOf(path)));
            archivo.writeObject(estructura);
            archivo.close();
            System.out.println("'" + path.toString() + " 'Guardado correctamente");
            return true;
        } catch (Exception e) {
            System.err.println("No se pudo guardar el archivo '" + path.toString() + "'");
        }
        return false;
    }

    public static boolean guardarEstructura(Object estructura, String path) {
        try {
            ObjectOutputStream archivo = new ObjectOutputStream(new FileOutputStream(path));
            archivo.writeObject(estructura);
            archivo.close();
            System.out.println("Guardado correctamente");
            return true;
        } catch (Exception e) {
            System.err.println("No se pudo guardar el archivo '" + path + "'");
        }
        return false;
    }
}
