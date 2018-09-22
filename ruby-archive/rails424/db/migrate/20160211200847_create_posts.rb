class CreatePosts < ActiveRecord::Migration
  def change
    create_table :posts do |t|
      t.integer :user_id
      t.integer :blog_id

      t.timestamps null: false
    end
  end
end
