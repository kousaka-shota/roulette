package com.example.roulette.entity;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
// setterを使用しなくても一括でデータをセットできる
@Builder
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER) // roleは複数保持されるため別テーブルを作る。非エンティティコレクションだよって明示する
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id")) // ↑のテーブル名はuser_rolesでuser.idとuser_roles.user_idで紐づける
    @Column(name = "role")
    private Set<String> roles; // roleは複数保持されるためSetを用いる。Listは順番を保持するがSetは保持しない

}
