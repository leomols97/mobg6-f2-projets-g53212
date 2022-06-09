import android.util.Log
import androidx.compose.runtime.Composable
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun CreateDB(){

    // Create a FirebaseFirestore object
    val db = Firebase.firestore

    // Pass data onto the firestore db, it must be of type hashmap
    val user = hashMapOf(
        "first" to "Ada",
        "last" to "Lovelace",
        "born" to 1815
    )

    // add the user data to the collection
    db.collection("users")
        .add(user)
        .addOnSuccessListener {
            Log.d("FB", "onCreate: ${it.id}")
        }
        .addOnFailureListener {
            Log.d("FB", "onCreate: ${it}")
        }
}