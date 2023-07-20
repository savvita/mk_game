package com.savita.machi_koro.db.hash;

import java.security.NoSuchAlgorithmException;
import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

public class Hash {
    public static String encode(String str) throws NoSuchAlgorithmException {
        Hasher hasher = Hashing.sha256().newHasher();
        hasher.putString(str, Charsets.UTF_8);
        return hasher.hash().toString();
    }
}
