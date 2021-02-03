package ru.centralhardware.asiec.inventory;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Hash {

    /**
     * get sha512 hash for giving string
     * @param str String to hash
     * @return hash for giving string
     */
    @SuppressWarnings("UnstableApiUsage")
    public static String getHash(String str){
        return Hashing.
                sha512().
                hashString(str, StandardCharsets.UTF_8).
                toString();
    }

}
