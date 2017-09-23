
package com.ezz.moviesapp.movies.model.cloud;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.ezz.moviesapp.movies.model.Trailer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrailersResponse implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Trailer> trailers = null;
    public final static Creator<TrailersResponse> CREATOR = new Creator<TrailersResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TrailersResponse createFromParcel(Parcel in) {
            TrailersResponse instance = new TrailersResponse();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.trailers, (com.ezz.moviesapp.movies.model.Trailer.class.getClassLoader()));
            return instance;
        }

        public TrailersResponse[] newArray(int size) {
            return (new TrailersResponse[size]);
        }

    }
    ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(trailers);
    }

    public int describeContents() {
        return  0;
    }

}
