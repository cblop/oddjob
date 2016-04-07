(ns client.handlers
    (:require [re-frame.core :as re-frame]
              [client.db :as db]))


(defn drop-nth [n coll]
  (keep-indexed #(if (not= %1 n) %2) coll))

(re-frame/register-handler
 :add-trope
 (fn [db [_ id]]
   (assoc db :our-tropes (conj (vec (:our-tropes db)) {:id nil :subverted false}))))

(re-frame/register-handler
 :subvert-trope
 (fn [db [_ n]]
   (let [sub (:subverted (nth (:our-tropes db) n))]
     (assoc db :our-tropes (assoc-in (:our-tropes db) [n :subverted] (not sub))))))

(re-frame/register-handler
 :change-trope
 (fn [db [_ n id]]
   (assoc db :our-tropes (assoc (:our-tropes db) n {:id id :subverted false}))))

(re-frame/register-handler
 :remove-trope
 (fn [db [_ n]]
   (let [a (:our-tropes db)]
         (assoc db :our-tropes (drop-nth n a)))))

(re-frame/register-handler
 :trope-selected
 (fn [db [_ n id]]
   (assoc-in db [:our-tropes n] {:id id :subverted false})))

(re-frame/register-handler
 :tab-changed
 (fn [db [_ tab-id]]
   (assoc db :current-tab tab-id)))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))
