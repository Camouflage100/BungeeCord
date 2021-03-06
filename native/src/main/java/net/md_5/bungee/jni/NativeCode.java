package net.md_5.bungee.jni;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.jni.cipher.BungeeCipher;

import java.io.*;

public final class NativeCode<T> {

    private final String name;
    private final Class<T> javaImpl;
    private final Class<T> nativeImpl;
    //
    private boolean loaded;

    public NativeCode(String name, Class<T> javaImpl, Class<T> nativeImpl) {
        this.name = name;
        this.javaImpl = javaImpl;
        this.nativeImpl = nativeImpl;
    }

    public static boolean isSupported() {
        return "Linux".equals(System.getProperty("os.name")) && "amd64"
            .equals(System.getProperty("os.arch"));
    }

    public T newInstance() {
        try {
            return (loaded) ? nativeImpl.newInstance() : javaImpl.newInstance();
        } catch (IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException("Error getting instance", ex);
        }
    }

    public boolean load() {
        if (!loaded && isSupported()) {
            String fullName = "bungeecord-" + name;

            try {
                System.loadLibrary(fullName);
                loaded = true;
            } catch (Throwable ignored) {
            }

            if (!loaded) {
                try (InputStream soFile = BungeeCipher.class.getClassLoader()
                    .getResourceAsStream(name + ".so")) {
                    // Else we will create and copy it to a temp file
                    File temp = File.createTempFile(fullName, ".so");
                    // Don't leave cruft on filesystem
                    temp.deleteOnExit();

                    try (OutputStream outputStream = new FileOutputStream(temp)) {
                        ByteStreams.copy(soFile, outputStream);
                    }

                    System.load(temp.getPath());
                    loaded = true;
                } catch (IOException ex) {
                    // Can't write to tmp?
                } catch (UnsatisfiedLinkError ex) {
                    System.out.println("Could not load native library: " + ex.getMessage());
                }
            }
        }

        return loaded;
    }
}
