import hashlib

def merge_seeds(*hashes) -> str | None:
    """
    Retorna um hash SHA-256 (64 caracteres hex) para uso como seed.
    Se nenhum hash for fornecido, a função não retorna nada.
    """
    if not hashes:
        return None
    
    if not all(isinstance(h, str) for h in hashes):
        raise ValueError("Todos os hashes devem ser strings.")

    seeds_combined = ''.join(hashes)
    return hashlib.sha256(seeds_combined.encode()).hexdigest()