package com.simplechat.core.use_cases.home_screen

import com.simplechat.core.domain.LastMessageDomain
import com.simplechat.core.domain.UserDomain
import kotlinx.coroutines.flow.Flow


interface GetLastMessages {
    operator suspend fun invoke()
        : Flow<List<LastMessageDomain>>
}