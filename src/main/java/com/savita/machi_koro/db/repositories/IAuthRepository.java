package com.savita.machi_koro.db.repositories;

import com.savita.machi_koro.db.models.Auth;
import com.savita.machi_koro.db.models.Register;
import com.savita.machi_koro.db.models.User;

public interface IAuthRepository {
    boolean checkUsername(String username);
    User signUp(Register auth);
    User signIn(Auth auth);
}
