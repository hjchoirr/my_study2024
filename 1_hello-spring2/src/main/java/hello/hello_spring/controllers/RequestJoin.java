package hello.hello_spring.controllers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestJoin {
    private String name;
}
