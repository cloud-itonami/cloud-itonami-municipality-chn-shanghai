(ns culture.facts-test
  (:require [clojure.edn :as edn]
            [kotoba.lang.text :as str]
            [clojure.test :refer [deftest is testing]]
            [culture.facts :as facts]))

(deftest shanghai-has-culture-basis
  (let [sb (facts/spec-basis "shanghai")]
    (is (= 6 (count sb)))
    (is (= (count sb) (count (set (map :culture/id sb)))) "ids are unique")
    (is (every? #(str/starts-with? (:culture/url %) "https://") sb))
    (is (every? #(= "shanghai" (:culture/municipality %)) sb))
    (is (every? #(= "CHN" (:culture/country %)) sb))
    (is (every? #(seq (:culture/summary %)) sb))
    (is (every? #(seq (:culture/name-local %)) sb))
    (is (every? #(= "2026-07-27" (:culture/retrieved-at %)) sb))))

(deftest unknown-municipality-has-no-basis
  (is (nil? (facts/spec-basis "beijing")))
  (is (nil? (facts/spec-basis "zzz"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["shanghai" "shenzhen"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["shenzhen"] (:missing-municipalities c)))))

(deftest by-kind-filters
  (is (= 3 (count (facts/by-kind "shanghai" :dish))))
  (is (= 2 (count (facts/by-kind "shanghai" :heritage))))
  (is (= ["shanghai.architecture.shikumen"]
         (mapv :culture/id (facts/by-kind "shanghai" :architecture))))
  (is (empty? (facts/by-kind "shenzhen" :dish))))

(deftest performing-art-is-absent-not-guessed
  (testing "the Huju (沪剧) source returned HTTP 404, so nothing was recorded from a page not read"
    (is (empty? (facts/by-kind "shanghai" :performing-art)))
    (is (str/includes? (:note (facts/coverage)) "404"))))

(deftest shared-origin-dishes-name-the-other-place-rather-than-claiming-shanghai-alone
  (testing "the sources tie these to Changzhou / Suzhou as well, and the summaries say so"
    (let [by-id (into {} (map (juxt :culture/id identity) (facts/spec-basis "shanghai")))]
      (is (str/includes? (:culture/summary (by-id "shanghai.dish.xiaolongbao")) "Changzhou"))
      (is (str/includes? (:culture/summary (by-id "shanghai.dish.shengjian-mantou")) "Suzhou")))))

(deftest tx-file-matches-catalog
  (let [tx (edn/read-string (slurp "data/culture-tx.edn"))
        flat (mapcat val (sort-by key facts/catalog))]
    (is (= (vec flat) (vec tx)))))

(deftest every-attribute-used-is-declared-in-the-schema
  (testing "schema/culture.edn is the same shape across every municipality-* sibling"
    (let [declared (set (keys (edn/read-string (slurp "schema/culture.edn"))))
          used (set (mapcat keys (mapcat val facts/catalog)))]
      (is (empty? (remove declared used))
          (str "undeclared: " (vec (remove declared used)))))))
