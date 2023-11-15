package com.artfil.bookshop.error;

import com.artfil.bookshop.error.exception.EntityNotFoundException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookShopExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(@NonNull Throwable ex, @NonNull DataFetchingEnvironment env) {
        log.error(ex.getMessage(), ex);

        if (ex instanceof EntityNotFoundException e)
            return buildError(env, ErrorType.NOT_FOUND, e.getMessage());

        return buildError(env, ErrorType.INTERNAL_ERROR, ex.getMessage());
    }

    private GraphQLError buildError(DataFetchingEnvironment env, ErrorType errorType, String message) {
        return GraphqlErrorBuilder.newError()
                .errorType(errorType)
                .message(message)
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }
}
