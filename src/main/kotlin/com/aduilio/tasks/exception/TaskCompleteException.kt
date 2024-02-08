package com.aduilio.tasks.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * To be thrown when an entity was not found.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class TaskCompleteException(message: String?) : RuntimeException(message) {

}