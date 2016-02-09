//
//  SelectAIViewController.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 30/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface SelectAIViewController : NSViewController
@property (weak) IBOutlet NSImageView *background;
@property (weak) IBOutlet NSButton *backButton;
@property (weak) IBOutlet NSTableView *tableView;
@property (weak) IBOutlet NSButton *modifyButton;
@property (weak) IBOutlet NSButton *deleteButton;
@property (weak) IBOutlet NSButton *exportButton;

@end
