package com.pies.platform.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.pies.platform.teachersActivity.AssignmentFragment;

public class Teachers_list extends AssignmentFragment {

    public Teachers_list() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery = databaseReference.child("Teachers_Added");
        // [END recent_posts_query]

        return recentPostsQuery;
    }
}
