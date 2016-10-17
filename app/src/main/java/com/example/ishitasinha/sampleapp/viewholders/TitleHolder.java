package com.example.ishitasinha.sampleapp.viewholders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ishitasinha.sampleapp.R;
import com.quintype.core.story.Story;
import com.quintype.core.story.StoryElement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by ishitasinha on 02/05/16.
 */
public class TitleHolder extends BaseElementHolder {

    TextView titleTextView,
            authorTextView,
            timeTextView;
    Story story;

    public TitleHolder (View itemView, Story story) {
        super(itemView);
        this.story = story;
    }

    @Override
    public void bind(StoryElement element) {
        titleTextView.setText(story.headline());
        authorTextView.setText(story.authorName());
        timeTextView.setText(formatDate(story.updatedAt()));
    }

    @Override
    public View getView() {
        return itemView;
    }

    @Override
    public boolean recreate() {
        return false;
    }

    public static TitleHolder create(ViewGroup parent, Story story) {
        View view = com.quintype.coreui.util.ViewUtils.inflate(R.layout.title_holder, parent);
        TitleHolder titleHolder = new TitleHolder(view, story);
        titleHolder.titleTextView = (TextView) view.findViewById(R.id.story_title);
        titleHolder.authorTextView = (TextView) view.findViewById(R.id.story_author);
        titleHolder.timeTextView = (TextView) view.findViewById(R.id.story_updated_at);
        return titleHolder;
    }

    private String formatDate(long milliseconds) {
        DateFormat sdf = new SimpleDateFormat("HH:mm', 'dd-MMM-yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        TimeZone tz = TimeZone.getDefault();
        sdf.setTimeZone(tz);
        return sdf.format(calendar.getTime());
    }

}
