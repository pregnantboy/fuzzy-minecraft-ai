//
//  RulesListViewController.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface RulesListViewController : NSViewController
@property (weak) IBOutlet NSTableView *tableView;
@property (weak) IBOutlet NSButton *backButton;
@property (weak) IBOutlet NSImageView *background;

@end
