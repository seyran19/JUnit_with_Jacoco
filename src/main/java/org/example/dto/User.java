package org.example.dto;

import lombok.Value;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Value(staticConstructor = "of")
public class User {

     int id;
     String name;
     int password;



}
