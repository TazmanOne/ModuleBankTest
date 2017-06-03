package modulebank.modulebanktestapp;

import com.orm.SugarRecord;


public class DBFavorite extends SugarRecord {
    String title;
    String link;
    String author;

    public DBFavorite() {
    }

    public DBFavorite(String title, String link, String author) {
        this.title = title;
        this.link = link;
        this.author = author;
    }


}
