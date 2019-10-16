package com.pushkar.musix2.service;

import com.pushkar.musix2.model.Artist;
import com.pushkar.musix2.model.Song;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

@Service
public class LastFmServiceImpl implements LastFmService {
    private String key = "f8a3aa6b6a254b26ad5eb84fd11f23a7";

    @Override
    public ArrayList<Song> getTopTracks() throws IOException {
        URL url = new URL("http://ws.audioscrobbler.com/2.0/?method=geo.gettoptracks&country=spain&api_key=" + key + "&format=json");
        String response = getResponseFromURL(url);
        JSONObject object = new JSONObject(response);
        return getAllFromJSONArray(object.getJSONObject("tracks").getJSONArray("track"));
    }

    @Override
    public ArrayList<Artist> getTopArtists() throws IOException {
        URL url = new URL("http://ws.audioscrobbler.com/2.0/?method=geo.gettopartists&country=spain&api_key=f8a3aa6b6a254b26ad5eb84fd11f23a7&format=json");
        String response = getResponseFromURL(url);
        JSONObject jsonObject = new JSONObject(response);
        ArrayList<Artist> artists = new ArrayList<>();
        for (Object artistObj : jsonObject.getJSONObject("topartists").getJSONArray("artist")) {
            JSONObject a = new JSONObject(artistObj.toString());
            Artist artist = new Artist();
            String id = a.getString("mbid");
            if (id == null) {
                id = randomString();
            }
            artist.setId(id);
            artist.setName(a.getString("name"));
            artist.setUrl(a.getString("url"));
            artists.add(artist);
        }
        return artists;
    }

    @Override
    public ArrayList<Song> search(String search) throws IOException {
        URL url = new URL("http://ws.audioscrobbler.com/2.0/?method=track.search&track=" + search + "&api_key=" + key + "&format=json");
        String response = getResponseFromURL(url);
        JSONArray array = new JSONObject(response).getJSONObject("results").getJSONObject("trackmatches").getJSONArray("track");
        return getAllFromJSONArray(array);
    }

    private ArrayList<Song> getAllFromJSONArray(JSONArray jsonArray) {
        ArrayList<Song> tracks = new ArrayList<>();
        for (Object s : jsonArray) {
            Song song = new Song();
            JSONObject obj = new JSONObject(s.toString());
            String id = obj.getString("mbid");
            if (id != null && !id.equals("")) {
                song.setId(id);
            } else {
                song.setId(randomString());
            }
            if (obj.has("name"))
                song.setName(obj.getString("name"));
            if (obj.has("url"))
                song.setUrl(obj.getString("url"));
            if (obj.has("duration"))
                song.setDuration(obj.getInt("duration"));
            if (obj.has("artist")) {
                try {
                    JSONObject a = obj.getJSONObject("artist");
                    Artist artist = new Artist();
                    String aid = a.getString("mbid");
                    if (aid != null && !aid.isEmpty()) {
                        artist.setId(aid);
                    } else {
                        artist.setId(randomString());
                    }
                    if (a.has("name"))
                        artist.setName(a.getString("name"));
                    if (a.has("url"))
                        artist.setUrl(a.getString("url"));
                    song.setArtist(artist);
                } catch (JSONException ex) {
                    Artist artist = new Artist();
                    artist.setId(randomString());
                    String name = obj.getString("artist");
                    if (name != null)
                        artist.setName(name);
                    else
                        artist.setName("");
                }
            }
            tracks.add(song);
        }
        return tracks;
    }

    private String randomString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private String getResponseFromURL(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();
            return response.toString();
        } else {
            return "";
        }
    }
}
