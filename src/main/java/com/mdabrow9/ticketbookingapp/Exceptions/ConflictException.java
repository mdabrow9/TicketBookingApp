package com.mdabrow9.ticketbookingapp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException
{
    public ConflictException()
    {
    }

    public ConflictException(String message)
    {
        super(message);
    }
}
