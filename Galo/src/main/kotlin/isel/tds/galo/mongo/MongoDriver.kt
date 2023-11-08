package isel.tds.galo.mongo


import com.mongodb.ConnectionString
import com.mongodb.MongoClientException
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.MongoClient
import com.mongodb.kotlin.client.MongoCollection
import com.mongodb.kotlin.client.MongoDatabase
import java.io.Closeable

private const val ENV_CONNECTION = "MONGO_CONNECTION"


class MongoDriver(nameDb: String? =null) : Closeable {
    val db: MongoDatabase
    private val client: MongoClient
    init {
        val envConnection = System.getenv(ENV_CONNECTION)
            ?: throw MongoClientException("Connection string in environment variable $ENV_CONNECTION is required")
        val dbName = requireNotNull(
            nameDb ?: ConnectionString(envConnection).database
        ) { "Database name is required in constructor or in connection string" }
        client = MongoClient.create(envConnection)
        db = client.getDatabase(dbName)
    }
    override fun close() = client.close()
}


class Collection<T: Any>(val collection: MongoCollection<T>)


inline fun <reified T: Any> MongoDriver.getCollection(id: String) =
    Collection(db.getCollection(id, T::class.java))


inline fun <reified T: Any> MongoDriver.getAllCollections() =
    db.listCollectionNames().toList().map { getCollection<T>(it) }


fun <T: Any> Collection<T>.getAllDocuments(): List<T> =
    collection.find().toList()


fun <T: Any, K> Collection<T>.getDocument(id: K): T? =
    collection.find(Filters.eq(id)).firstOrNull()


fun <T: Any> Collection<T>.insertDocument(doc: T): Boolean =
    collection.insertOne(doc).insertedId!=null


fun <T: Any, K> Collection<T>.replaceDocument(id: K, doc: T): Boolean =
    collection.replaceOne(Filters.eq(id),doc).modifiedCount==1L


fun <T: Any, K> Collection<T>.deleteDocument(id: K): Boolean =
    collection.deleteOne(Filters.eq("_id",id)).deletedCount==1L


fun <T: Any> Collection<T>.deleteAllDocuments(): Boolean =
    collection.deleteMany(Filters.exists("_id")).wasAcknowledged()