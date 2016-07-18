package com.pies.platform.teachersActivity;
        import com.pies.platform.R;

        import android.app.Fragment;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;

        import android.os.Bundle;
        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.GridLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.pies.platform.Login;
        import com.pies.platform.R;
        import com.pies.platform.admin.Admin_dashboard;
        import com.pies.platform.admin.Create_Account;
        import com.pies.platform.admin.model.AdminAdapter;
        import com.pies.platform.admin.model.Admin_Item;
        import com.pies.platform.model_users.Users;
        import com.pies.platform.teachersActivity.model.Teacher_Adapter;
        import com.pies.platform.teachersActivity.model.teacher_item;

        import java.util.ArrayList;
        import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class AssignmentFragment extends android.support.v4.app.Fragment{
    private List<teacher_item> movieList = new ArrayList<>();
    private RecyclerView recyclerView2;
    private Teacher_Adapter mAdapter;
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]
    FirebaseAuth mFirebaseAuth;
    private static final String TAG = "ManagerHome";
    private FirebaseUser mFirebaseUser;
    private String teacherNam, teacherEmail;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView tName, tEmail;
    private String url;
    private String userId;
    DatabaseReference myRootref = FirebaseDatabase.getInstance().getReference();

    ProgressDialog progressDialog;
    public AssignmentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_assignment, container, false);
return root;
    }
}