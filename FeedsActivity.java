package meghna.com.blogistic1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class FeedsActivity extends AppCompatActivity {
    String t[] = new String[6];
    String receivedUrl;
    LinearLayoutManager manager;
    RecyclerView rvpage;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pb = (ProgressBar) findViewById(R.id.pbb);
        if (getIntent() != null) {
            int idh = getIntent().getIntExtra("meghna.com.blogistic1.ab", 0);
            switch (idh) {
                case 1:
                    technology();
                    break;
                case 2:
                    Startups();
                    break;
                case 3:
                    Marketing();
                    break;
                case 4:
                    news();
                    break;
                case 5:
                    photography();
                    break;
                case 6:
                    Cooking();
                    break;
                case 7:
                    quotes();
                    break;
                case 8:
                    fashion();
                    break;
                case 9:
                    Games();
                    break;
                case 10:
                    Entertainment();
                    break;
                case 11:
                    Travel();
                    break;
                case 12:
                    Beauty();
                    break;
            }
            int id = getIntent().getIntExtra("meghna.com.blogistic1.abcd", 0);
            switch (id) {
                case 1:
                    receivedUrl = t[0];
                    break;
                case 2:
                    receivedUrl = t[1];
                    break;
                case 3:
                    receivedUrl = t[2];
                    break;
                case 4:
                    receivedUrl = t[3];
                    break;
                case 5:
                    receivedUrl = t[4];
                    break;
                case 6:
                    receivedUrl = t[5];
            }
        }
        rvpage = (RecyclerView) findViewById(R.id.rvpage);
        manager = new LinearLayoutManager(this);
        rvpage.setLayoutManager(manager);
        startfeedTask();
    }

    public void startfeedTask() {
        MyFeedDownloader mFeedDownloader = new MyFeedDownloader();
        mFeedDownloader.execute();
    }

    public void updateText(String updates) {
        // header.append(updates);
    }

    public void addfeedstoList(ArrayList<HashMap<String, String>> result) {
        //Log.i("message", "" + result);
        if (result != null) {
            MyAdapter adapter = new MyAdapter(result);
            rvpage.setAdapter(adapter);
        } else {
            Toast.makeText(FeedsActivity.this, "addfeedtolist empty", Toast.LENGTH_SHORT).show();
        }
    }

    private class MyFeedDownloader extends
            AsyncTask<Void, String, ArrayList<HashMap<String, String>>> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(
                Void... params) {
            ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
            URL url;
            HttpURLConnection connection;
            InputStream stream = null;
            try {
                url = new URL(receivedUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                stream = connection.getInputStream();
                DocumentBuilderFactory factory = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder builder = null;
                try {
                    builder = factory.newDocumentBuilder();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
                Document document = null;
                try {
                    document = builder.parse(stream);
                } catch (SAXException e) {
                    e.printStackTrace();
                }
                Element rootElement = document.getDocumentElement();
                NodeList itemList = rootElement.getElementsByTagName("item");
                Log.i("msg", "" + rootElement.getTagName());
                Node currentItem, currNode;
                NodeList childList;
                Node child;
                int count = 0;

                HashMap<String, String> currentMap;
                // result = new ArrayList<HashMap<String, String>>();

                for (int i = 0; i < itemList.getLength(); i++) {
                    currentItem = itemList.item(i);
                    childList = currentItem.getChildNodes();
                    currentMap = new HashMap<String, String>();

                    for (int j = 0; j < childList.getLength(); j++) {
                        currNode = childList.item(j);
                        //Log.d("message", currNode.getNodeName() + "\n");
                        if (currNode.getNodeName().equalsIgnoreCase("title")) {

                            currentMap.put("title", currNode.getTextContent());
                            Log.d("message", currNode.getTextContent());
                            publishProgress("title->"
                                    + currNode.getTextContent());

                        } else if (currNode.getNodeName().equalsIgnoreCase(
                                "pubDate")) {
                            currentMap
                                    .put("pubdate", currNode.getTextContent());
                            Log.d("messagedate", currNode.getTextContent());
                            publishProgress("date->"
                                    + currNode.getTextContent());
                        } else if (currNode.getNodeName().equalsIgnoreCase("description")) {
                            currentMap
                                    .put("description", currNode.getTextContent());
                            publishProgress("description->"
                                    + currNode.getTextContent());
                        }
                        /*else if (currNode.getNodeName().equalsIgnoreCase(
                                "description")) {
                            if (currNode.hasChildNodes()) {
                                for (child = currNode.getFirstChild(); child != null; child = child.getNextSibling()) {
                                    if (child.getNodeName() == "img") {
                                        count++;
                                        currentMap.put("thumbnail", child.getAttributes().getNamedItem("src").getNodeValue());
                                        publishProgress("image->" + child.getAttributes().getNamedItem("src").getNodeValue());
                                    } else {
                                        currentMap
                                                .put("description", currNode.getTextContent());
                                        publishProgress("description->"
                                                + currNode.getTextContent());
                                    }
                                }
                            }
                        }*/
                        else if (currNode.getNodeName().equalsIgnoreCase(
                                "link")) {
                            currentMap
                                    .put("link", currNode.getTextContent());
                            Log.d("messagelink", currNode.getTextContent());
                            publishProgress("link->"
                                    + currNode.getTextContent());
                        } else if (currNode.getNodeName().equalsIgnoreCase(
                                "content:encoded")) {
                            count++;
                            if (count == 1) {
                                for (child = currNode.getFirstChild(); child != null; child = child.getNextSibling()) {
                                    if (child.getNodeName() == "a") {
                                        {
                                            currentMap.put("thumbnail", child.getAttributes().getNamedItem("src").getNodeValue());
                                            publishProgress("image->" + child.getAttributes().getNamedItem("src").getNodeValue());
                                        }
                                    }
                                }
                            }
                        } else if (currNode.getNodeName().equalsIgnoreCase("media:thumbnail")) {
                            count++;
                            if (count == 1) {
                                Log.d("message", currNode.getAttributes().item(1).getTextContent());
                                currentMap.put("thumbnail", currNode
                                        .getAttributes().getNamedItem("url")
                                        .getTextContent());
                                publishProgress("image->" + currNode.getAttributes().item(0)
                                        .getTextContent());
                            }
                        } else if (currNode.getNodeName().equalsIgnoreCase("image")) {
                            count++;
                            if (count == 1) {
                                for (child = currNode.getFirstChild(); child != null; child = child.getNextSibling()) {
                                    if (child.getNodeName() == "url") {
                                        String key = child.getNodeValue();
                                        currentMap.put("thumbnail", key);
                                        publishProgress("image->" + key);
                                    }
                                }
                            }
                        }
                    }
                    count = 0;
                    result.add(currentMap);
                    Log.i("current map", result.toString());
                }
            } catch (Exception e) {
                publishProgress(e.toString());
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(String... values) {

            updateText(values[0] + "\n");
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
            addfeedstoList(result);
        }
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView imgpage;
        TextView tvpage, tvdata;
        TextView tvdate;

        public Holder(View itemView) {
            super(itemView);
            imgpage = (ImageView) itemView.findViewById(R.id.icon);
            tvpage = (TextView) itemView.findViewById(R.id.tv);
            tvdata = (TextView) itemView.findViewById(R.id.data);
            tvdate = (TextView) itemView.findViewById(R.id.date);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<Holder> {
        private final ArrayList<HashMap<String, String>> result;

        public MyAdapter(ArrayList<HashMap<String, String>> result) {
            this.result = result;
        }

        @Override
        public FeedsActivity.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.card_row_layout, parent, false);
            Holder holder = new Holder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(FeedsActivity.Holder holder, final int position) {
            final HashMap<String, String> singleFeed = result.get(position);
            holder.tvpage.setText(singleFeed.get("title"));
            holder.tvdate.setText(singleFeed.get("pubdate"));
            String description = singleFeed.get("description");
            fetchImageFromDescription(description, holder.tvdata, holder.imgpage);
            //holder.tvdata.setText(htmlDesc.toString());
            try {
                holder.imgpage.setImageURI(Uri.parse(new URL(singleFeed.get("thumbnail")).toString()));
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("message", e.toString());
            }
            holder.tvdata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Intent s = new Intent(Intent.ACTION_VIEW, Uri.parse(singleFeed.get("link")));
                    Intent s = new Intent(FeedsActivity.this, WebActivity.class);
                    s.putExtra("meghna.com.blogistic1.url", singleFeed.get("link"));
                    startActivity(s);
                }
            });
        }

        private void fetchImageFromDescription(String description, TextView tvdata, ImageView imgpage) {
            /*Pattern pattern = Pattern.compile("src\\s*=\\s*['\\\"]([^'\\\"]*?)['\\\"][^>]*?");
            Matcher matcher = pattern.matcher(description);
            if (matcher.matches()) {
                String imageTag = matcher.group(1);
                String url = matcher.group(2);

            }*/
            String s = "<img src=\"";
            int ix = description.indexOf(s) + s.length();
            try {
                String imgSrc = description.substring(ix, description.indexOf("\"", ix + 1));
                Log.d("aweeee", imgSrc);

                String StrippedDesc = description.replaceAll(imgSrc, "");
                Spanned htmlDesc = Html.fromHtml(StrippedDesc);
                String trim = htmlDesc.toString().trim();
                String desc = trim.replaceAll("\n", " ");
                tvdata.setText(desc);
            } catch (Exception e) {
                Spanned htmlDesc = Html.fromHtml(description);
                String trim = htmlDesc.toString().trim();
                String desc = trim.replaceAll("\n", " ");
                tvdata.setText(desc);
            }
            Glide.with(FeedsActivity.this).load(R.mipmap.ic).into(imgpage);
        }

        @Override
        public int getItemCount() {
            return result.size();
        }
    }

    void Beauty() {
        t[0] = "http://thebeautydepartment.com/feed/";
        t[1] = "http://feeds.feedburner.com/frmheadtotoe?format=xml";
        t[2] = "http://fulltextrssfeed.com/www.fashionista.com/index.xml";
        t[3] = "http://www.refinery29.com/rss.xml";
        t[4] = "http://feeds.specificfeeds.com/popsugar-beauty-s-specific-feed";
        t[5] = "http://feeds.feedburner.com/intothegloss/VUQZ?format=xml";
    }

    void Games() {
        t[0] = "http://www.polygon.com/rss/index.xml";
        t[1] = "http://in.ign.com/feed.xml";
        t[2] = "https://www.engadget.com/rss.xml";
        t[3] = "http://feeds.gawker.com/kotaku/full?format=xml";
        t[4] = "http://feeds.feedburner.com/RockPaperShotgun?format=xml";
        t[5] = "https://www.penny-arcade.com/feed";
    }

    void fashion() {
        t[0] = "http://fashionista.com/.rss/excerpt/";
        t[1] = "http://feeds.feedburner.com/TheArtOfManliness?format=xml";
        t[2] = "http://feeds.feedburner.com/uncrate?format=xml";
        t[3] = "http://feeds.coolhunting.com/ch";
        t[4] = "http://feeds.feedburner.com/TheSartorialist?format=xml";
        t[5] = "http://becauseimaddicted.net/feed";
    }

    void technology() {

        t[0] = "feeds.feedburner.com/theverge/xowH?format=xml";
        t[1] = "https://techcrunch.com/feed/";
        t[2] = "http://feeds.feedburner.com/androidcentral?format=xml";
        t[3] = "http://www.engadget.com/rss.xml";
        t[4] = "http://www.lifehacker.co.in/rss_tag_section_feeds.cms?query=android";
        t[5] = "http://www.wired.com/category/gear/feed/";
    }

    void Entertainment() {
        t[0] = "http://www.ew.com/microsites/static/rss/podcasts/insideTV/index.xml";
        t[1] = "http://www.bollywoodhungama.com/rss/news.xml";
        t[2] = "http://www.missmalini.com/feed/";
        t[3] = "http://www.empireonline.com/podcast/podcast.xml";
        t[4] = "http://feeds2.feedburner.com/slashfilm?format=xml";
        t[5] = "http://feeds.feedburner.com/thr/news?format=xml";
    }

    void Startups() {
        t[0] = "http://www.forbes.com/entrepreneurs/feed/";
        t[1] = "http://feeds.feedburner.com/venturebeat/SZYF?format=xml";
        t[2] = "http://feeds.feedburner.com/entrepreneur/startingabusiness.rss?format=xml";
        t[3] = "http://feeds.feedburner.com/Quicksprout?format=xml";
        t[4] = "http://fourhourworkweek.com/blog/feed/";
        t[5] = "http://feeds.feedburner.com/AVc?format=xml";
    }

    void news() {
        t[0] = "http://timesofindia.indiatimes.com/rssfeedstopstories.cms";
        t[1] = "http://feeds.abcnews.com/abcnews/topstories"; //PUT AT T4
        t[2] = "http://feeds.bbci.co.uk/news/rss.xml?edition=uk";//AT T3
        t[3] = "http://rss.cnn.com/rss/edition.rss?format=xml";//AT T1
        t[4] = "http://www.thehindu.com/news/?service=rss";//AT T3
        t[5] = "https://news.google.co.in/news?cf=all&hl=en&pz=1&ned=in&output=rss";
    }

    void Marketing() {
        t[0] = "http://feeds.mashable.com/Mashable?format=xml";
        t[1] = "http://feeds.feedburner.com/Quicksprout?format=xml";
        t[2] = "http://blog.hubspot.com/marketing/rss.xml";
        t[3] = "http://feedpress.me/mozblog?format=xml";
        t[4] = "http://feeds.copyblogger.com/copyblogger?format=xml";
        t[5] = "http://feeds.marketingland.com/mktingland?format=xml";
    }

    void photography() {
        t[0] = "https://theimpossiblecool.tumblr.com/rss#_=_";
        t[1] = "http://www.bostonglobe.com/rss/bigpicture";
        t[2] = "http://nationalgeographicphotos.tumblr.com/rss";
        t[3] = "http://feeds.feedburner.com/DigitalPhotographySchool";
        t[4] = "https://500px.com/fresh.rss";
        t[5] = "http://feeds.feedburner.com/stuckincustoms?format=xml";
    }

    void Cooking() {
        t[0] = "http://www.foodandwine.com/feeds/latest_recipes";
        t[1] = "http://bakingbites.com/feed/";
        t[2] = "http://feeds.feedburner.com/pinch-of-yum?format=xml";
        t[3] = "http://feeds.cookinglight.com/CookingLight/Cooking101";
        t[4] = "http://www.tasteofhome.com/rss";
        t[5] = "http://www.awesomecuisine.com/feed";
    }

    void quotes() {
        t[0] = "http://feeds.feedburner.com/brainyquote/QUOTEBR?format=xml";
        t[1] = "http://feeds.feedburner.com/quotationspage/qotd?format=xml";
        t[2] = "http://www.daily-motivational-quote.com/Daily-Motivational-Quote.xml";
        t[3] = "http://feeds.feedburner.com/theysaidso/qod?format=xml";
        t[4] = "https://www.quotesdaddy.com/feed";
        t[5] = "http://feeds.feedburner.com/QuoteSnack?format=xml";
    }

    void Travel() {
        t[0] = "http://intelligenttravel.nationalgeographic.com/author/intelligenttravel/feed/";
        t[1] = "http://thepointsguy.com/feed/?tid=1002";
        t[2] = "http://www.travelandleisure.com/blogs/feed";
        t[3] = "http://feeds.feedblitz.com/millionmilesecrets";
        t[4] = "http://feeds.feedburner.com/stuckincustoms?format=xml";
        t[5] = "http://rss.nytimes.com/services/xml/rss/nyt/Travel.xml";
    }
}