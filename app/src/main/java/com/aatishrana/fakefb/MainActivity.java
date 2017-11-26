package com.aatishrana.fakefb;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.aatishrana.fakefb.data.MainRepository;
import com.aatishrana.fakefb.network.APIClient;
import com.aatishrana.fakefb.network.APIService;
import com.aatishrana.fakefb.settings.Settings;
import com.aatishrana.fakefb.newsFeed.NewsFeed;
import com.aatishrana.fakefb.notification.Notification;
import com.aatishrana.fakefb.findFriend.FindFriend;

import java.util.ArrayList;
import java.util.List;

import static com.aatishrana.fakefb.utils.Const.DEFAULT_DATA;

public class MainActivity extends AppCompatActivity
{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        APIService service = APIClient.getClient().create(APIService.class);
        repository = new MainRepository(service, getMySharedPref(), getDefaultSampleJson());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setTitleMargin(0, 0, 0, 0);
        toolbar.setSubtitle("");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                tab.setIcon(R.drawable.ic_sample_white);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
                tab.setIcon(R.drawable.ic_sample);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });
    }

    private SharedPreferences getMySharedPref()
    {
        SharedPreferences pref = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        if (!pref.contains(DEFAULT_DATA))
            pref.edit().putString(DEFAULT_DATA.toLowerCase(), getDefaultSampleJson()).commit();
        return pref;
    }

    private void setupTabIcons()
    {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_sample_white);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_sample);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_sample);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_sample);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewsFeed(), "");
        adapter.addFragment(new FindFriend(), "");
        adapter.addFragment(new Notification(), "");
        adapter.addFragment(new Settings(), "");
        viewPager.setAdapter(adapter);
    }

    public MainRepository getRepository()
    {
        return repository;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager)
        {
            super(manager);
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentTitleList.get(position);
        }
    }

    private String getDefaultSampleJson()
    {
        return "{\"data\":{\"news_feed\":{\"story\":[{\"first_name\":\"peter\",\"last_name\":\"parker\",\"user_pic_url\":\"http://image.guardian.co.uk/sys-images/Film/Pix/gallery/2004/07/13/pparker3.jpg\",\"watched\":false},{\"first_name\":\"tony\",\"last_name\":\"stark\",\"user_pic_url\":\"https://pbs.twimg.com/profile_images/885796425593790464/xyqKbSt9_400x400.jpg\",\"watched\":false},{\"first_name\":\"steve\",\"last_name\":\"roger\",\"user_pic_url\":\"https://i.pinimg.com/736x/60/07/22/6007228e2e6661e473ef25e17c98d6c6--cris-evans-this-is-awesome.jpg\",\"watched\":true}],\"feed\":[{\"rank\":1,\"type\":\"post\",\"user_pic_url\":\"https://pbs.twimg.com/profile_images/885796425593790464/xyqKbSt9_400x400.jpg\",\"user_name\":\"Tony Stark\",\"time\":\"5 min ago\",\"location\":\"California\",\"privacy\":1,\"emotions\":26,\"comments\":33,\"views\":56,\"shares\":133,\"data\":{\"content_text\":\"Sometimes you gotta run before you can walk\"}},{\"rank\":2,\"type\":\"post\",\"user_pic_url\":\"https://pbs.twimg.com/profile_images/885796425593790464/xyqKbSt9_400x400.jpg\",\"user_name\":\"Tony Stark\",\"time\":\"23 min ago\",\"location\":\"california\",\"privacy\":1,\"emotions\":299,\"data\":{\"content_text\":\"Its not enough to be against something. You have to be for something better.\"}},{\"rank\":3,\"type\":\"post\",\"user_pic_url\":\"http://cdn.mos.cms.futurecdn.net/7MzHKweGbbQ7AnRVVDxz78-320-80.jpg\",\"user_name\":\"Bruce Wayne\",\"data\":{\"content_image\":{\"height\":800,\"hexcode\":\"#000000\",\"url\":\"http://www.udreplicas.com/resized_images/-800-height-nomex-13.jpg\",\"width\":1053},\"content_text\":\"Everything is impossible until somebody does it.\"}},{\"rank\":4,\"type\":\"shared\",\"user_pic_url\":\"http://image.guardian.co.uk/sys-images/Film/Pix/gallery/2004/07/13/pparker3.jpg\",\"user_name\":\"Peter Parker shared Uncle Ben's post\",\"time\":\"23 min ago\",\"location\":\"New York\",\"privacy\":1,\"emotions\":260,\"shared_from\":{\"user_pic_url\":\"https://pbs.twimg.com/profile_images/875786257950949376/DdXOHXrm_400x400.jpg\",\"user_name\":\"Uncle Ben\",\"time\":\"4 years ago\",\"location\":\"New York\",\"privacy\":1},\"data\":{\"content_text\":\"With Great power comes great reponsibility\",\"content_image\":{\"height\":250,\"width\":250,\"hexcode\":\"#dfdfdf\",\"url\":\"http://1.bp.blogspot.com/_Z15RhXK8e4E/TQAOtQ6x2FI/AAAAAAAAAXg/qUlKIaTIpd8/s1600/caution.gif\"}}},{\"rank\":5,\"type\":\"album_of_two\",\"user_pic_url\":\"https://i.pinimg.com/736x/60/07/22/6007228e2e6661e473ef25e17c98d6c6--cris-evans-this-is-awesome.jpg\",\"user_name\":\"Steve Rogers\",\"data\":{\"image_one_url\":\"https://images.halloweencostumes.com/products/43504/1-1/captain-america-womens-costume.jpg\",\"image_two_url\":\"https://vignette1.wikia.nocookie.net/marvelcinematicuniverse/images/4/42/Captain-America-Civil-War-Promo-Art-costume-first-look.jpg\"}},{\"rank\":6,\"type\":\"album_of_three\",\"user_pic_url\":\"https://www.unitetheleague.com/images/logo.png\",\"user_name\":\"JL\",\"data\":{\"image_one_url\":\"https://upload.wikimedia.org/wikipedia/en/1/17/Batman-BenAffleck.jpg\",\"image_two_url\":\"http://junkee.com/wp-content/uploads/2017/06/Wonder-Woman-Movie-Sexism-Feminism.jpg\",\"image_three_url\":\"http://cdn.darkhorizons.com/wp-content/uploads/2016/08/david-ayer-keen-to-do-a-superman-film.jpg\"}},{\"rank\":7,\"type\":\"album_of_four\",\"user_pic_url\":\"https://botw-pd.s3.amazonaws.com/styles/logo-thumbnail/s3/082010/logo_the_avengers.png\",\"user_name\":\"Avengers\",\"data\":{\"image_one_url\":\"http://blog.karachicorner.com/wp-content/uploads/2013/06/Iron-Man-Mark-IV-Closeup.jpg\",\"image_two_url\":\"http://s2.filmwatch.com/news/1116601_1.jpg\",\"image_three_url\":\"https://scontent-sea1-1.cdninstagram.com/t51.2885-15/s480x480/e35/12534450_1843637549196350_445131687_n.jpg\",\"image_four_url\":\"https://i.ytimg.com/vi/vBkrEclZ6dY/hqdefault.jpg\"}}]},\"friends\":{\"request\":[{\"first_name\":\"Dead\",\"last_name\":\"Pool\",\"user_pic_url\":\"https://s3.amazonaws.com/ffe-ugc/intlportal2/dev-temp/en-US/__59a48ed7599d0.jpg\",\"mutual_friends\":6},{\"first_name\":\"Logan\",\"last_name\":\"\",\"user_pic_url\":\"https://upload.wikimedia.org/wikipedia/en/b/bf/Wolverine_AKA_James_%22Logan%22_Howlett.png\",\"mutual_friends\":9}],\"suggestions\":[{\"first_name\":\"scarlet\",\"last_name\":\"witch\",\"user_pic_url\":\"http://theg6group.com/scarlet-witch-from-avengers-age-of-ultron-closeup-of-her-face.jpg\",\"mutual_friends\":19},{\"first_name\":\"Dianna\",\"last_name\":\"\",\"user_pic_url\":\"https://pbs.twimg.com/profile_images/888942780554326016/ACbjeDG2_400x400.jpg\",\"mutual_friends\":12},{\"first_name\":\"Alfred\",\"last_name\":\"pennyworth\",\"user_pic_url\":\"https://s1.reutersmedia.net/resources/r/?m=02\\u0026d=20120718\\u0026t=2\\u0026i=631523588\\u0026r=CBRE86H0PCG00\\u0026w=1280\",\"mutual_friends\":2},{\"first_name\":\"Oliver\",\"last_name\":\"queen\",\"user_pic_url\":\"https://static.comicvine.com/uploads/original/11119/111190794/4778782-3828613029-Olive.jpg\",\"mutual_friends\":1},{\"first_name\":\"Laurel\",\"last_name\":\"lance\",\"user_pic_url\":\"https://poptraction.files.wordpress.com/2016/04/goodbye-laurel.jpg\",\"mutual_friends\":0},{\"first_name\":\"Kara\",\"last_name\":\"Danvers\",\"user_pic_url\":\"https://uploads.wornontv.net/2017/05/kars-white-stripe-sweater.jpg\",\"mutual_friends\":0}]},\"notification\":[{\"rank\":1,\"title\":\"Deadpool taged you in a photo\",\"user_pic_url\":\"https://s3.amazonaws.com/ffe-ugc/intlportal2/dev-temp/en-US/__59a48ed7599d0.jpg\",\"time\":\"2 min ago\",\"icon\":1,\"read\":false},{\"rank\":2,\"title\":\"Kara tagged you in a post\",\"user_pic_url\":\"https://uploads.wornontv.net/2017/05/kars-white-stripe-sweater.jpg\",\"time\":\"4 hours ago\",\"icon\":1,\"read\":true},{\"rank\":3,\"title\":\"Bary Allen invited you to like TEAM FLASH\",\"user_pic_url\":\"https://sistergeeks.files.wordpress.com/2015/05/flash-closeup.jpg\",\"time\":\"3 Days ago\",\"icon\":2,\"read\":true},{\"rank\":4,\"title\":\"Lex luthor added 7 photos in Injustice League\",\"user_pic_url\":\"https://i2.wp.com/batman-news.com/wp-content/uploads/2017/01/Lex-Luthor-Arkham-BvS.jpg?fit=1200%2C667\\u0026quality=85\\u0026strip=info\\u0026ssl=1\\u0026resize=200%2C150\",\"time\":\"4 Days ago\",\"icon\":1,\"read\":true},{\"rank\":5,\"title\":\"Black Widow replied to your comment on post\",\"user_pic_url\":\"http://pop-critica.com/wp-content/uploads/2014/02/captain-america-the-winter-soldier-skype-promo-black-widow-close-up.jpg\",\"time\":\"5 Days ago\",\"icon\":1,\"read\":true},{\"rank\":6,\"title\":\"Death Strok's birthday was yesterday\",\"user_pic_url\":\"https://i.pinimg.com/736x/c2/78/24/c2782418c09416b5219b2d4b0f4b54d2--deadpool-deathstroke-deadshot.jpg\",\"time\":\"Yesterday at 9:54 AM\",\"icon\":1,\"read\":true}]}}";
//        else if (i == 2)
//            return "{\"data\":{\"news_feed\":{\"story\":[{\"first_name\":\"sample\",\"last_name\":\"sample\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"watched\":false},{\"first_name\":\"sample2\",\"last_name\":\"sample2\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"watched\":false},{\"first_name\":\"sample3\",\"last_name\":\"sample3\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"watched\":false},{\"first_name\":\"sample4\",\"last_name\":\"sample4\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"watched\":false}],\"feed\":[{\"rank\":1,\"type\":\"post\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"time\":\"5 min ago\",\"location\":\"chandigarh\",\"privacy\":1,\"emotions\":26,\"comments\":33,\"views\":56,\"shares\":133,\"data\":{\"content_text\":\"blah blah blah blah blah blah blah\",\"content_image\":{\"height\":300,\"width\":500,\"hexcode\":\"#dfdfdf\",\"url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}}},{\"rank\":2,\"type\":\"post\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"time\":\"23 min ago\",\"location\":\"chandigarh\",\"privacy\":1,\"emotions\":26,\"data\":{\"content_text\":\"Only blah blah blah blah blah blah blah\"}},{\"rank\":3,\"type\":\"post\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"data\":{\"content_text\":\"Only only only blah blah blah blah blah blah blah\"}},{\"rank\":4,\"type\":\"shared\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana shared Dilpreet Singh's photo\",\"time\":\"23 min ago\",\"location\":\"chandigarh\",\"privacy\":1,\"emotions\":26,\"shared_from\":{\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Dilpreet Singh\",\"time\":\"23 min ago\",\"location\":\"chandigarh\",\"privacy\":1},\"data\":{\"content_text\":\"Only blah blah blah blah blah blah blah\",\"content_image\":{\"height\":300,\"width\":500,\"hexcode\":\"#dfdfdf\",\"url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}}},{\"rank\":5,\"type\":\"album_of_two\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"data\":{\"image_one_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_two_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}},{\"rank\":6,\"type\":\"album_of_three\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"data\":{\"image_one_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_two_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_three_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}},{\"rank\":7,\"type\":\"album_of_four\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"data\":{\"image_one_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_two_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_three_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_four_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}},{\"rank\":8,\"type\":\"album_of_many\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"user_name\":\"Aatish Rana\",\"data\":{\"count\":7,\"image_one_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_two_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_three_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\",\"image_four_url\":\"https://bt-wpstatic.freetls.fastly.net/wp-content/blogs.dir/616/files/2017/10/uber-eats-in-marysville.jpg\"}}]},\"friends\":{\"request\":[{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6}],\"suggestions\":[{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6},{\"first_name\":\"Sample one\",\"last_name\":\"Sample one\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"mutual_friends\":6}]},\"notification\":[{\"rank\":1,\"title\":\"Some one did something to someone blah blah blah...\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"time\":\"23 min ago\",\"icon\":1,\"read\":false},{\"rank\":2,\"title\":\"Some one did something to someone blah blah blah...\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"time\":\"23 min ago\",\"icon\":1,\"read\":false},{\"rank\":3,\"title\":\"Some one did something to someone blah blah blah...\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"time\":\"23 min ago\",\"icon\":1,\"read\":false},{\"rank\":4,\"title\":\"Some one did something to someone blah blah blah...\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"time\":\"23 min ago\",\"icon\":1,\"read\":false},{\"rank\":5,\"title\":\"Some one did something to someone blah blah blah...\",\"user_pic_url\":\"https://ips.pepitastore.com/storefront/img/resized/squareenix-store-v2/cb6e1679808b73a75ac880ae130f198d_1920_KR.jpg\",\"time\":\"23 min ago\",\"icon\":1,\"read\":false}]}}";
//        else
//            return "{\"data\":{\"news_feed\":{\"story\":[{\"first_name\":\"peter\",\"last_name\":\"parker\",\"user_pic_url\":\"http://image.guardian.co.uk/sys-images/Film/Pix/gallery/2004/07/13/pparker3.jpg\",\"watched\":false},{\"first_name\":\"tony\",\"last_name\":\"stark\",\"user_pic_url\":\"https://pbs.twimg.com/profile_images/885796425593790464/xyqKbSt9_400x400.jpg\",\"watched\":false},{\"first_name\":\"steve\",\"last_name\":\"roger\",\"user_pic_url\":\"https://i.pinimg.com/736x/60/07/22/6007228e2e6661e473ef25e17c98d6c6--cris-evans-this-is-awesome.jpg\",\"watched\":true}],\"feed\":[{\"rank\":1,\"type\":\"post\",\"user_pic_url\":\"https://pbs.twimg.com/profile_images/885796425593790464/xyqKbSt9_400x400.jpg\",\"user_name\":\"Tony Stark\",\"time\":\"5 min ago\",\"location\":\"California\",\"privacy\":1,\"emotions\":26,\"comments\":33,\"views\":56,\"shares\":133,\"data\":{\"content_text\":\"Sometimes you gotta run before you can walk\"}},{\"rank\":2,\"type\":\"post\",\"user_pic_url\":\"https://pbs.twimg.com/profile_images/885796425593790464/xyqKbSt9_400x400.jpg\",\"user_name\":\"Tony Stark\",\"time\":\"23 min ago\",\"location\":\"california\",\"privacy\":1,\"emotions\":299,\"data\":{\"content_text\":\"Its not enough to be against something. You have to be for something better.\"}},{\"rank\":3,\"type\":\"post\",\"user_pic_url\":\"http://cdn.mos.cms.futurecdn.net/7MzHKweGbbQ7AnRVVDxz78-320-80.jpg\",\"user_name\":\"Bruce Wayne\",\"data\":{\"content_image\":{\"height\":800,\"hexcode\":\"#000000\",\"url\":\"http://www.udreplicas.com/resized_images/-800-height-nomex-13.jpg\",\"width\":1053},\"content_text\":\"Everything is impossible until somebody does it.\"}},{\"rank\":4,\"type\":\"shared\",\"user_pic_url\":\"http://image.guardian.co.uk/sys-images/Film/Pix/gallery/2004/07/13/pparker3.jpg\",\"user_name\":\"Peter Parker shared Uncle Ben's post\",\"time\":\"23 min ago\",\"location\":\"New York\",\"privacy\":1,\"emotions\":260,\"shared_from\":{\"user_pic_url\":\"https://pbs.twimg.com/profile_images/875786257950949376/DdXOHXrm_400x400.jpg\",\"user_name\":\"Uncle Ben\",\"time\":\"4 years ago\",\"location\":\"New York\",\"privacy\":1},\"data\":{\"content_text\":\"With Great power comes great reponsibility\",\"content_image\":{\"height\":250,\"width\":250,\"hexcode\":\"#dfdfdf\",\"url\":\"http://1.bp.blogspot.com/_Z15RhXK8e4E/TQAOtQ6x2FI/AAAAAAAAAXg/qUlKIaTIpd8/s1600/caution.gif\"}}},{\"rank\":5,\"type\":\"album_of_two\",\"user_pic_url\":\"https://i.pinimg.com/736x/60/07/22/6007228e2e6661e473ef25e17c98d6c6--cris-evans-this-is-awesome.jpg\",\"user_name\":\"Steve Rogers\",\"data\":{\"image_one_url\":\"https://images.halloweencostumes.com/products/43504/1-1/captain-america-womens-costume.jpg\",\"image_two_url\":\"https://vignette1.wikia.nocookie.net/marvelcinematicuniverse/images/4/42/Captain-America-Civil-War-Promo-Art-costume-first-look.jpg\"}},{\"rank\":6,\"type\":\"album_of_three\",\"user_pic_url\":\"https://www.unitetheleague.com/images/logo.png\",\"user_name\":\"JL\",\"data\":{\"image_one_url\":\"https://upload.wikimedia.org/wikipedia/en/1/17/Batman-BenAffleck.jpg\",\"image_two_url\":\"http://junkee.com/wp-content/uploads/2017/06/Wonder-Woman-Movie-Sexism-Feminism.jpg\",\"image_three_url\":\"http://cdn.darkhorizons.com/wp-content/uploads/2016/08/david-ayer-keen-to-do-a-superman-film.jpg\"}},{\"rank\":7,\"type\":\"album_of_four\",\"user_pic_url\":\"https://botw-pd.s3.amazonaws.com/styles/logo-thumbnail/s3/082010/logo_the_avengers.png\",\"user_name\":\"Avengers\",\"data\":{\"image_one_url\":\"http://blog.karachicorner.com/wp-content/uploads/2013/06/Iron-Man-Mark-IV-Closeup.jpg\",\"image_two_url\":\"http://s2.filmwatch.com/news/1116601_1.jpg\",\"image_three_url\":\"https://scontent-sea1-1.cdninstagram.com/t51.2885-15/s480x480/e35/12534450_1843637549196350_445131687_n.jpg\",\"image_four_url\":\"https://i.ytimg.com/vi/vBkrEclZ6dY/hqdefault.jpg\"}}]},\"friends\":{\"request\":[{\"first_name\":\"Dead\",\"last_name\":\"Pool\",\"user_pic_url\":\"https://s3.amazonaws.com/ffe-ugc/intlportal2/dev-temp/en-US/__59a48ed7599d0.jpg\",\"mutual_friends\":6},{\"first_name\":\"Logan\",\"last_name\":\"\",\"user_pic_url\":\"https://upload.wikimedia.org/wikipedia/en/b/bf/Wolverine_AKA_James_%22Logan%22_Howlett.png\",\"mutual_friends\":9}],\"suggestions\":[{\"first_name\":\"scarlet\",\"last_name\":\"witch\",\"user_pic_url\":\"http://theg6group.com/scarlet-witch-from-avengers-age-of-ultron-closeup-of-her-face.jpg\",\"mutual_friends\":19},{\"first_name\":\"Dianna\",\"last_name\":\"\",\"user_pic_url\":\"https://pbs.twimg.com/profile_images/888942780554326016/ACbjeDG2_400x400.jpg\",\"mutual_friends\":12},{\"first_name\":\"Alfred\",\"last_name\":\"pennyworth\",\"user_pic_url\":\"https://s1.reutersmedia.net/resources/r/?m=02\\u0026d=20120718\\u0026t=2\\u0026i=631523588\\u0026r=CBRE86H0PCG00\\u0026w=1280\",\"mutual_friends\":2},{\"first_name\":\"Oliver\",\"last_name\":\"queen\",\"user_pic_url\":\"https://static.comicvine.com/uploads/original/11119/111190794/4778782-3828613029-Olive.jpg\",\"mutual_friends\":1},{\"first_name\":\"Laurel\",\"last_name\":\"lance\",\"user_pic_url\":\"https://poptraction.files.wordpress.com/2016/04/goodbye-laurel.jpg\",\"mutual_friends\":0},{\"first_name\":\"Kara\",\"last_name\":\"Danvers\",\"user_pic_url\":\"https://uploads.wornontv.net/2017/05/kars-white-stripe-sweater.jpg\",\"mutual_friends\":0}]},\"notification\":[{\"rank\":1,\"title\":\"Deadpool taged you in a photo\",\"user_pic_url\":\"https://s3.amazonaws.com/ffe-ugc/intlportal2/dev-temp/en-US/__59a48ed7599d0.jpg\",\"time\":\"2 min ago\",\"icon\":1,\"read\":false},{\"rank\":2,\"title\":\"Kara tagged you in a post\",\"user_pic_url\":\"https://uploads.wornontv.net/2017/05/kars-white-stripe-sweater.jpg\",\"time\":\"4 hours ago\",\"icon\":1,\"read\":true},{\"rank\":3,\"title\":\"Bary Allen invited you to like TEAM FLASH\",\"user_pic_url\":\"https://sistergeeks.files.wordpress.com/2015/05/flash-closeup.jpg\",\"time\":\"3 Days ago\",\"icon\":2,\"read\":true},{\"rank\":4,\"title\":\"Lex luthor added 7 photos in Injustice League\",\"user_pic_url\":\"https://i2.wp.com/batman-news.com/wp-content/uploads/2017/01/Lex-Luthor-Arkham-BvS.jpg?fit=1200%2C667\\u0026quality=85\\u0026strip=info\\u0026ssl=1\\u0026resize=200%2C150\",\"time\":\"4 Days ago\",\"icon\":1,\"read\":true},{\"rank\":5,\"title\":\"Black Widow replied to your comment on post\",\"user_pic_url\":\"http://pop-critica.com/wp-content/uploads/2014/02/captain-america-the-winter-soldier-skype-promo-black-widow-close-up.jpg\",\"time\":\"5 Days ago\",\"icon\":1,\"read\":true},{\"rank\":6,\"title\":\"Death Strok's birthday was yesterday\",\"user_pic_url\":\"https://i.pinimg.com/736x/c2/78/24/c2782418c09416b5219b2d4b0f4b54d2--deadpool-deathstroke-deadshot.jpg\",\"time\":\"Yesterday at 9:54 AM\",\"icon\":1,\"read\":true}]}}";
    }
}
