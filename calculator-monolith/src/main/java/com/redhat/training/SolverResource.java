package com.redhat.training;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.redhat.training.operation.Operation;
import com.redhat.training.registry.OperationRegistry;
import com.redhat.training.service.SolverService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SolverResource implements SolverService {
    private static final Logger LOG = LoggerFactory.getLogger(SolverResource.class);

    @Inject
    OperationRegistry opsReg;

    private List<Operation> operationList = opsReg.getOperations();
    @Override
    public Float solve(@PathParam("equation") final String equation) {
        LOG.info("Solving '{}'", equation);
        for (Operation operation : operationList) {
            Float result = operation.apply(equation);
            if (result != null) {
                LOG.info("Solved '{} = {}'", equation, result);
                return result;
            }
        }

        throw new WebApplicationException(
                Response.status(Response.Status.BAD_REQUEST).entity("Unable to parse: " + equation).build());
    }

}
