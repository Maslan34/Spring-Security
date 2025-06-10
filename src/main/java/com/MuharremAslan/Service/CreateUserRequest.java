package com.MuharremAslan.Service;

import com.MuharremAslan.Model.ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    String username;
    String password;
    String name;
    Set<ROLE> authorities ;
}
