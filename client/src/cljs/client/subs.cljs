(ns client.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))


(defn in?
  "true if coll contains elm"
  [coll elm]
  (some #(= elm %) coll))

(re-frame/register-sub
 :show
 (fn [db _]
   (reaction (:show @db))))

(re-frame/register-sub
 :story
 (fn [db _]
   (reaction (:story @db))))

(re-frame/register-sub
 :subverted?
 (fn [db [_ n]]
   (let [trope (nth (:our-tropes @db) n)]
     (reaction (:subverted trope)))))

(re-frame/register-sub
 :chars-for-archetypes
 (fn [db [_ arches]]
   (let [chars (map (fn [x] (filter #(in? (:archetypes %) x) (:characters @db))) arches)]
     (reaction chars))
   ))

(re-frame/register-sub
 :chars-for-archetype
 (fn [db [_ arch]]
   (let [chars (filter #(in? (:archetypes %) arch) (:characters @db))]
     (reaction chars))
   ))

(re-frame/register-sub
 :archetypes
 (fn [db [_ n]]
   (let [tropeid (:id (nth (:our-tropes @db) n))
         archetypes (:archetypes (first (filter #(= tropeid (:id %)) (:tropes @db))))]
     (reaction archetypes))))

(re-frame/register-sub
 :our-tropes
 (fn [db _]
   (reaction (:our-tropes @db))))

(re-frame/register-sub
 :characters
 (fn [db _]
   (reaction (:characters @db))))

(re-frame/register-sub
 :tropes
 (fn [db _]
   (reaction (:tropes @db))))


(re-frame/register-sub
 :charname-for-id
 (fn [db [_ id]]
   (let [match (first (filter #(= id (:id %)) (:characters @db)))]
     (reaction (:label match)))))

(re-frame/register-sub
 :trope-for-id
 (fn [db [_ id]]
   (let [match (first (filter #(= id (:id %)) (:tropes @db)))]
     (reaction match))))

(re-frame/register-sub
 :current-tab
 (fn [db _]
   (reaction (:current-tab @db))))
