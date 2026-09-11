(ns ordinance.facts-test
  (:require [clojure.edn :as edn]
            [kotoba.lang.text :as str]
            [clojure.test :refer [deftest is testing]]
            [ordinance.facts :as facts]))

(deftest shanghai-has-spec-basis
  (let [sb (facts/spec-basis "shanghai")]
    (is (= 2 (count sb)))
    (is (every? #(str/includes? (:ordinance/url %) "sh.gov.cn") sb)
        "every citation is an official Shanghai municipal URL")
    (is (every? #(= :official-sh-gov-cn (:ordinance/url-provenance %)) sb))
    (is (every? #(= "CHN" (:ordinance/country %)) sb))
    (is (every? #(= "2026-07-27" (:ordinance/retrieved-at %)) sb))))

(deftest unknown-municipality-has-no-spec-basis
  (is (nil? (facts/spec-basis "beijing")) "the Beijing catalog lives in its own repo")
  (is (nil? (facts/spec-basis "zzz"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["shanghai" "shenzhen"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["shenzhen"] (:missing-municipalities c)))))

(deftest both-entries-are-consolidated-ordinances-not-amendment-decisions
  (testing "unlike the Beijing sibling's waste entry, both pages read here are the ordinance itself"
    (is (every? #(= :ordinance (:ordinance/kind %)) (facts/spec-basis "shanghai")))
    (is (str/includes? (:note (facts/coverage)) "CONSOLIDATED"))))

(deftest the-waste-entry-carries-both-dates-read-verbatim-from-the-official-pdf
  (let [waste (first (facts/by-topic "shanghai" :waste-management))]
    (is (= "2019-01-31" (:ordinance/enacted-date waste)))
    (is (str/includes? (:ordinance/number waste) "第十五届人民代表大会第二次会议通过")
        "the FULL congress, not its standing committee -- as the PDF header states")
    (is (str/includes? (:ordinance/number waste) "2019年7月1日起施行")
        "from 第六十五条, read verbatim")
    (is (str/ends-with? (:ordinance/url waste) ".pdf"))))

(deftest the-smoking-entry-records-the-second-revision-and-the-e-cigarette-scope
  (let [smoking (first (facts/by-topic "shanghai" :smoking-control))]
    (is (= "2009-12-10" (:ordinance/enacted-date smoking)))
    (is (= "2022-10-28" (:ordinance/last-revised-date smoking))
        "the SECOND 修正, not the first (2016-11-11)")
    (is (contains? (:ordinance/topic smoking) :e-cigarettes)
        "第一条 names 电子烟 explicitly -- this is the statute's own scope, not an inference")))

(deftest by-topic-filters
  (is (= ["shanghai.gonggong-changsuo-kongzhi-xiyan-tiaoli-2009"]
         (mapv :ordinance/id (facts/by-topic "shanghai" :public-health))))
  (is (empty? (facts/by-topic "shanghai" :housing)))
  (is (empty? (facts/by-topic "shenzhen" :waste-management))))

(deftest tx-file-matches-catalog
  (let [tx (edn/read-string (slurp "data/datascript-tx.edn"))
        flat (mapcat val (sort-by key facts/catalog))]
    (is (= (vec flat) (vec tx)))))

(deftest every-attribute-used-is-declared-in-the-schema
  (testing "schema/ordinance.edn is deliberately IDENTICAL across every municipality-* sibling -- that uniformity is what lets the federated query join across them, so a new attribute here would be a silent divergence"
    (let [declared (set (keys (edn/read-string (slurp "schema/ordinance.edn"))))
          used (set (mapcat keys (mapcat val facts/catalog)))]
      (is (empty? (remove declared used))
          (str "undeclared: " (vec (remove declared used)))))))
