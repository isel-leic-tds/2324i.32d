package isel.tds.galo.storage

interface Storage<Key, Data> {
    /**
     * Creates a new entry in the storage.
     * @param key the key of the entry.
     * @param data the data to store.
     */
    fun create(key: Key, data: Data)

    /**
     * Reads an entry from the storage.
     * @param key the key of the entry.
     * @return the data stored or null if the key does not exist.
     */
    fun read(key: Key): Data?

    /**
     * Updates an entry in the storage.
     * @param key the key of the entry.
     * @param data the new data to store.
     */
    fun update(key: Key, data: Data)

    /**
     * Deletes an entry from the storage.
     * @param key the key of the entry.
     */
    fun delete(key: Key)
}