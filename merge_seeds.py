import hashlib

def merge_seeds(*hashes_with_names) -> str | None:
    """
    Recebe pares (nome_da_api, hash) e imprime o nome de cada API utilizada.
    Retorna um hash SHA-256 (64 caracteres hex) combinando todos os hashes.
    Se nenhum hash for fornecido, retorna None.
    """
    if not hashes_with_names:
        return None

    # Validação dos dados
    for item in hashes_with_names:
        if not (isinstance(item, tuple) and len(item) == 2):
            raise ValueError("Cada argumento deve ser uma tupla (nome_da_api, hash).")

        nome_api, hash_val = item
        if not isinstance(nome_api, str) or not isinstance(hash_val, str):
            raise ValueError("Nome da API e hash devem ser strings.")

        # Logar o nome da API que foi carregada
        print(f"{nome_api} foi carregado.")

    # Combinar todos os hashes
    seeds_combined = ''.join(hash_val for _, hash_val in hashes_with_names)
    return hashlib.sha256(seeds_combined.encode()).hexdigest()