package ru.lvov.SecondLabSpringBoot.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.lvov.SecondLabSpringBoot.model.Response;

import java.util.UUID;

@Service
@Qualifier("ModifyOperationUidResponceService")
public class ModifyOperationUidResponceService implements ModifyResponseService{
    @Override
    public Response modify(Response response){
        UUID uuid = UUID.randomUUID();
        response.setOperationUid(uuid.toString());
        return response;
    }

}
