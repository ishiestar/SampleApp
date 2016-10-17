package com.example.ishitasinha.sampleapp.utility;

import android.os.Parcel;
import android.os.Parcelable;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.quintype.core.data.NavMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ishitasinha on 03/06/16.
 */
public class Sections implements ParentListItem, Parcelable {

    private NavMenu menuItem;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    int position;
    private List<NavMenu> mSubsections = new ArrayList<>();

    public Sections() {
    }

    public void setMenuItem(NavMenu menuItem) {
        this.menuItem = menuItem;
    }

    public void addSubsection(NavMenu subsection) {
        mSubsections.add(subsection);
    }

    public NavMenu getMenuItem() {
        return menuItem;
    }

    public String getName() {
        return menuItem.displayName();
    }

    @Override
    public List<NavMenu> getChildItemList() {
        return mSubsections;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.menuItem, flags);
        dest.writeInt(this.position);
        dest.writeTypedList(this.mSubsections);
    }

    protected Sections(Parcel in) {
        this.menuItem = in.readParcelable(NavMenu.class.getClassLoader());
        this.position = in.readInt();
        this.mSubsections = in.createTypedArrayList(NavMenu.CREATOR);
    }

    public static final Parcelable.Creator<Sections> CREATOR = new Parcelable.Creator<Sections>() {
        @Override
        public Sections createFromParcel(Parcel source) {
            return new Sections(source);
        }

        @Override
        public Sections[] newArray(int size) {
            return new Sections[size];
        }
    };
}
