(ns casper.core)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Helpers

(defn- transition [slide transition]
  (assoc-in slide [:slide/section :data-transition] transition))

(defn- make-slide* [{:keys [background]} & body]
  #:slide {:body (into [:div.text-center] body)
           :section {:data-background background
                     :style "padding:10vmin"}})

(defn- render [{:slide/keys [body section hide-logo?]}]
  [:section.flex.flex-col.justify-between.h-full.text-left.text-5xl
   section
   (when-not hide-logo?
     [:strong "brightin"])
   body
   (when-not hide-logo?
     [:div.text-3xl.text-right.text-black.italic
      "Yann Vanhalewyn"])])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Public

(defn transition-group [[in during out] [first & others]]
  (let [middle (butlast others)
        end    (last others)]
    [(transition first (str in " " during "-out" " "))
     (map #(transition % during) middle)
     (transition end (str during "-in " out " "))]))

(defn render-slides [slides]
  (for [slide (flatten slides)]
    ^{:keys (:slide/key slide)}
    (render slide)))

#?(:clj
   (defmacro defslide [name opts & body]
     `(def ~name ~(apply make-slide* opts body))))
