package com.example.safepass

class FonteLocal : FonteDado { // obtém dados locais para serem usados na seed
    override suspend fun obterDado(): String {
        return System.nanoTime().toString() + System.currentTimeMillis().toString()
    }
}
