package ru.lvov.SecondLabSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.lvov.SecondLabSpringBoot.model.Response;

import java.sql.PreparedStatement;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}
