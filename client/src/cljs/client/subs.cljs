(ns client.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))


(defn in?
  "true if coll contains elm"
  [coll elm]
  (some #(= elm %) coll))

(re-frame/register-sub
 :chars-for-archetype
 (fn [db [_ arch]]
   (let [chars (filter #(in? (:archetypes %) arch) (:characters @db))]
     (reaction chars))
   ))

(re-frame/register-sub
 :archetypes
 (fn [db [_ n]]
   (let [tropeid (nth (:our-tropes @db) n)
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
 :current-tab
 (fn [db _]
   (reaction (:current-tab @db))))
