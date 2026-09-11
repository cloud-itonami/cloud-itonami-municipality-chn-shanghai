(ns ordinance.facts
  "Municipal-ordinance compliance catalog for Shanghai (上海市) -- the second
  CHN member of the cloud-itonami-municipality-* compliance-fact family
  (ADR-2607141700), after cloud-itonami-municipality-chn-beijing. Gap
  recorded in superproject ADR-2607277000.

  Every entry cites an OFFICIAL Shanghai municipal URL -- never fabricated.
  An ordinance not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url/number.

  Both entries were verified on 2026-07-27, and unlike the Beijing sibling
  BOTH are the consolidated ordinance text rather than an amendment
  decision:

  - 生活垃圾管理条例: the official PDF hosted by 上海市城市管理行政执法局
    (cgzf.sh.gov.cn) was downloaded and its pages read directly. Its header
    line states verbatim 「（2019年1月31日上海市第十五届人民代表大会第二次
    会议通过）」 -- note the FULL congress, not its standing committee -- and
    its final article, 第六十五条, states 「本条例自2019年7月1日起施行。」
    Both dates below come from those two lines, not from a summary.
  - 公共场所控制吸烟条例: read from cgzf.sh.gov.cn. Its header line states
    通过 2009-12-10 (十三届人大常委会第十五次会议), 第一次修正 2016-11-11
    (十四届第三十三次会议), 第二次修正 2022-10-28 (十五届第四十五次会议).
    第一条 names 电子烟 (e-cigarettes) explicitly as within scope, which is
    why :topic carries :e-cigarettes -- the statute text says so, this repo
    is not inferring it.")

(def catalog
  "municipality-slug -> vector of ordinance entries."
  {"shanghai"
   [{:ordinance/id "shanghai.shenghuo-laji-guanli-tiaoli-2019"
     :ordinance/title "上海市生活垃圾管理条例 (Shanghai Municipal Regulations on Domestic Waste Management)"
     :ordinance/municipality "shanghai"
     :ordinance/country "CHN"
     :ordinance/kind :ordinance
     ;; The 施行日 is carried inside :ordinance/number rather than as its own
     ;; attribute, because schema/ordinance.edn is deliberately IDENTICAL
     ;; across every municipality-* sibling -- that uniformity is what lets
     ;; the federated query join across them.
     :ordinance/number "2019年1月31日上海市第十五届人民代表大会第二次会议通过（第六十五条：本条例自2019年7月1日起施行）"
     :ordinance/url "https://cgzf.sh.gov.cn/cmsres/d8/d8ce8e01225e47d8a53ec21ebc7391df/a01e7ee5f5350e793a02480784d0ace7.pdf"
     :ordinance/url-provenance :official-sh-gov-cn
     :ordinance/enacted-date "2019-01-31"
     :ordinance/retrieved-at "2026-07-27"
     :ordinance/topic #{:waste-management :environment}}
    {:ordinance/id "shanghai.gonggong-changsuo-kongzhi-xiyan-tiaoli-2009"
     :ordinance/title "上海市公共场所控制吸烟条例 (Shanghai Municipal Regulations on Smoking Control in Public Places)"
     :ordinance/municipality "shanghai"
     :ordinance/country "CHN"
     :ordinance/kind :ordinance
     :ordinance/number "2009年12月10日上海市第十三届人民代表大会常务委员会第十五次会议通过（2016年11月11日第一次修正、2022年10月28日第二次修正）"
     :ordinance/url "https://cgzf.sh.gov.cn/channel_89/20210811/18c5956b08754e40ad58f593a35503d3.html"
     :ordinance/url-provenance :official-sh-gov-cn
     :ordinance/enacted-date "2009-12-10"
     :ordinance/last-revised-date "2022-10-28"
     :ordinance/retrieved-at "2026-07-27"
     :ordinance/topic #{:public-health :smoking-control :e-cigarettes}}]})

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
      :note (str "cloud-itonami-municipality-chn-shanghai (family ADR-2607141700, "
                 "gap recorded in ADR-2607277000): "
                 (count (get catalog "shanghai")) " Shanghai entries seeded with "
                 "official sh.gov.cn citations read 2026-07-27. Unlike the "
                 "Beijing sibling, both are the CONSOLIDATED ordinance text "
                 "(the waste one read from the official PDF, header + 第六十五条 "
                 "verbatim). Extend `ordinance.facts/catalog`, never fabricate "
                 "an id/url/number.")})))

(defn by-topic [muni topic]
  (filterv #(contains? (:ordinance/topic %) topic) (spec-basis muni)))
