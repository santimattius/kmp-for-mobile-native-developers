//
//  Model.swift
//  iosApp
//
//  Created by Santiago Mattiauda on 9/2/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

enum GameServiceError: Error {
    case invalidURL
    case missingData
}

typealias GameResponse = [GameDto]

struct GameDto: Codable {
    let id: Int
    let title: String
    let thumbnail: String
    let shortDescription: String
    let gameURL: String
    let genre, platform, publisher, developer: String
    let releaseDate: String
    let freetogameProfileURL: String
    
    enum CodingKeys: String, CodingKey {
        case id, title, thumbnail
        case shortDescription = "short_description"
        case gameURL = "game_url"
        case genre, platform, publisher, developer
        case releaseDate = "release_date"
        case freetogameProfileURL = "freetogame_profile_url"
    }
}
extension GameDto{
    
    func asDomainModel() -> Game{
        return Game(id: Int64(self.id), title: self.title, thumbnail: self.thumbnail, shortDescription: self.shortDescription, publisher: self.publisher, developer: self.developer)
    }
}
