(ns culture.facts
  "Regional-culture catalog for Shanghai (上海市) -- local dishes, heritage
  sites and vernacular architecture, piggybacked onto this municipality
  compliance repo per ADR-2607171400
  (cloud-itonami-municipality-culture-catalog, in com-junkawasaki/root),
  sibling namespace to `ordinance.facts` (ADR-2607141700).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.

  Two notes on honesty, both visible in the data rather than only here:

  - `xiaolongbao` and `shengjian-mantou` both carry a summary that names
    the OTHER place the source ties them to (Changzhou / Suzhou). Neither
    is presented as purely Shanghainese, because the sources do not say
    that. They are in a Shanghai catalog because the sources tie the
    best-known form to Shanghai, and the summary says exactly that much.
  - No :performing-art entry. Huju (沪剧), the obvious Shanghainese-opera
    candidate, has no article at the URL that was tried (HTTP 404), and
    nothing was recorded from a source that was not read. Beijing's
    sibling catalog does have one; Shanghai's does not yet.")

(def catalog
  "municipality-slug -> vector of culture entries."
  {"shanghai"
   [{:culture/id "shanghai.dish.xiaolongbao"
     :culture/name "Xiaolongbao"
     :culture/name-local "小笼包"
     :culture/municipality "shanghai"
     :culture/country "CHN"
     :culture/kind :dish
     :culture/summary "A type of Chinese tangbao traditionally prepared in a xiaolong, a small bamboo steaming basket. The source attributes the most popular variety to Changzhou in Jiangsu; the Shanghai-style version, originating in Nanxiang (a former village now part of Shanghai), is the internationally best known."
     :culture/url "https://en.wikipedia.org/wiki/Xiaolongbao"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-27"}
    {:culture/id "shanghai.dish.shengjian-mantou"
     :culture/name "Shengjian mantou"
     :culture/name-local "生煎馒头"
     :culture/municipality "shanghai"
     :culture/country "CHN"
     :culture/kind :dish
     :culture/summary "Small pan-fried baozi described by the source as a specialty of Suzhou AND Shanghai, and one of the most common breakfast items in Shanghai since the early 1920s."
     :culture/url "https://en.wikipedia.org/wiki/Shengjian_mantou"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-27"}
    {:culture/id "shanghai.dish.benbang-cuisine"
     :culture/name "Shanghai cuisine (benbang / Hu cuisine)"
     :culture/name-local "本帮菜"
     :culture/municipality "shanghai"
     :culture/country "CHN"
     :culture/kind :dish
     :culture/summary "The regional culinary style originating in Shanghai, also called Hu cuisine (沪菜); characterised by a considerable amount of oil and soy sauce giving dishes a red, shiny appearance, aiming for lightness of flavour while being mellower and slightly sweet relative to other Chinese regional cuisines."
     :culture/url "https://en.wikipedia.org/wiki/Shanghai_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-27"}
    {:culture/id "shanghai.heritage.the-bund"
     :culture/name "The Bund"
     :culture/name-local "外滩"
     :culture/municipality "shanghai"
     :culture/country "CHN"
     :culture/kind :heritage
     :culture/summary "A waterfront area and protected historical district in central Shanghai, centred on a section of Zhongshan Road along the Huangpu River; it holds roughly 52 buildings in Western architectural styles including Art Deco, Beaux-Arts and Neoclassical, many of which formerly housed foreign banks and trading companies during the treaty-port era."
     :culture/url "https://en.wikipedia.org/wiki/The_Bund"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-27"}
    {:culture/id "shanghai.heritage.yu-garden"
     :culture/name "Yu Garden"
     :culture/name-local "豫园"
     :culture/municipality "shanghai"
     :culture/country "CHN"
     :culture/kind :heritage
     :culture/summary "An extensive Chinese garden beside the City God Temple in the north-east of the Old City of Shanghai, built from 1559 during the Ming dynasty by Pan Yunduan; declared a national monument in 1982 and a key site under state-level protection."
     :culture/url "https://en.wikipedia.org/wiki/Yu_Garden"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-27"}
    {:culture/id "shanghai.architecture.shikumen"
     :culture/name "Shikumen"
     :culture/name-local "石库门"
     :culture/municipality "shanghai"
     :culture/country "CHN"
     :culture/kind :architecture
     :culture/summary "A traditional Shanghainese architectural style combining Western and Chinese elements, first appearing in the 1860s; at their peak there were 9,000 shikumen buildings in Shanghai, some 60% of the city's housing stock, a proportion now much lower as residents moved to modern apartments."
     :culture/url "https://en.wikipedia.org/wiki/Shikumen"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-27"}]})

(defn spec-basis [muni] (get catalog muni))

(defn coverage
  ([] (coverage (keys catalog)))
  ([munis]
   (let [have (filter catalog munis)
         missing (remove catalog munis)]
     {:requested (count munis)
      :covered (count have)
      :covered-municipalities (vec (sort have))
      :missing-municipalities (vec (sort missing))
      :note (str "cloud-itonami-municipality-chn-shanghai culture catalog "
                 "(ADR-2607171400): " (count (get catalog "shanghai"))
                 " Shanghai entries, each with a fetched-and-read citation. "
                 "No :performing-art entry -- the Huju (沪剧) source tried "
                 "returned HTTP 404 and nothing was recorded from a page that "
                 "was not read. Extend `culture.facts/catalog`, never "
                 "fabricate an id/url.")})))

(defn by-kind [muni kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis muni)))
