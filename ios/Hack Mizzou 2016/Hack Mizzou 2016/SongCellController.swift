//
//  SongCellController.swift
//  Hack Mizzou 2016
//
//  Created by Reiker Seiffe on 10/14/16.
//  Copyright © 2016 Reiker R Seiffe. All rights reserved.
//

import UIKit

class SongCellController: UITableViewCell {
    
    @IBOutlet weak var songLabel: UILabel!
    @IBOutlet weak var artistLabel: UILabel!
    @IBOutlet weak var scoreLabel: UILabel!
    @IBOutlet weak var downButton: UIButton!
    @IBOutlet weak var upButton: UIButton!

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    
    @IBAction func voteDown(sender: AnyObject) {
        //send songID and downvote to API
        print("I downvoted")
    }
    
    @IBAction func voteUp(sender: AnyObject) {
        //send songID and upvote to API
        print("I upvoted")
    }
    

}
