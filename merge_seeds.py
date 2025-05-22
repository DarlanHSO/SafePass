import hashlib

def merge_seeds(*seeds) -> str | None:
    """
    Recebe tuplas (nome_da_api, hash) e imprime o nome de cada API utilizada.
    Concatena os hashes e retorna o SHA-256 resultante.
    """
    if not seeds:
        return None

    try:
        for nome_api, hash_val in seeds:
            print(f"{nome_api} foi carregado.")
    except (TypeError, ValueError):
        raise ValueError("Todos os argumentos devem ser tuplas (nome_da_api, hash).")

    combined = ''.join(hash_val for _, hash_val in seeds)
    return hashlib.sha256(combined.encode()).hexdigest()
