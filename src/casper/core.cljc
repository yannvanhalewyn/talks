(ns casper.core)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Helpers

(defn- transition [slide transition]
  (assoc slide :slide/transition transition))

(defn- empty-layout [body]
  {::render body})

(defn- section-props [{:slide/keys [class
                                    background-image
                                    background-color
                                    transition]}]
  {:class class
   :style {:padding "10vmin"}
   :data-background-image background-image
   :data-background-color (when-not background-image
                            background-color)
   :data-transition transition})

(defn- render [slide layout]
  (let [layout-result (layout (:slide/body slide))]
    [:section.flex.flex-col.justify-between.h-full.text-left
     (section-props (merge slide layout-result))
     (::render layout-result)]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Public

(defn colorize [& segments]
  (into [:span]
        (for [s segments]
          (if (sequential? s)
            [:span {:class (str "text-" (name (second s)))}
             (first s)]
            s))))

(defn enumeration [& items]
  (into [:ul.w-32.mx-auto.mt-8.text-lg.text-left.text-gray-800]
        (for [i items]
          [:li.fragment.fade-in i])))

(defn transition-group [[in during out] [first & others]]
  (let [middle (butlast others)
        end    (last others)]
    [(transition first (str in "-in " during "-out"))
     (map #(transition % during) middle)
     (transition end (str during "-in " out "-out"))]))

(defn render-slides [slides layouts]
  (for [slide (flatten slides)]
    ^{:keys (:slide/key slide)}
    (render slide
      (get (assoc layouts :layout/none empty-layout)
           (:slide/layout slide)
           (:layout/default layouts)))))

(defn make-slide [slide body]
  (assoc slide :slide/body body))

#?(:clj
   (defmacro defslide [name slide body]
     (let [slide (assoc slide :slide/key name)]
       `(def ~name ~(make-slide slide body)))))
