package modulebank.modulebanktestapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 02.06.2017.
 */

class StackAnswersModel implements Serializable {

    @SerializedName("items")
    private List<Items> items;
    @SerializedName("has_more")
    private boolean hasMore;
    @SerializedName("quota_max")
    private int quotaMax;
    @SerializedName("quota_remaining")
    private int quotaRemaining;

    public List<Items> getItems() {
        return items;
    }

    public boolean getHasMore() {
        return hasMore;
    }

    public int getQuotaMax() {
        return quotaMax;
    }

    public int getQuotaRemaining() {
        return quotaRemaining;
    }

    public static class Owner {


        @SerializedName("profile_image")
        private String profileImage;

        @SerializedName("display_name")
        private String displayName;

        public Owner(String displayName) {
            this.displayName = displayName;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public String getDisplayName() {
            return displayName;
        }


    }

    public static class Items {

        @SerializedName("owner")
        private Owner owner;
        @SerializedName("view_count")
        private int viewCount;
        @SerializedName("answer_count")
        private int answerCount;
        @SerializedName("score")
        private int score;
        @SerializedName("link")
        private String link;
        @SerializedName("title")
        private String title;

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public int getViewCount() {
            return viewCount;
        }

        public int getAnswerCount() {
            return answerCount;
        }

        public int getScore() {
            return score;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


    }
}
