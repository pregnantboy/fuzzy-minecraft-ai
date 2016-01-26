//
//  EditorViewController.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface EditorViewController : NSViewController
@property (weak) IBOutlet NSButton *backButton;
@property (weak) IBOutlet NSTableView *inputTableView;
@property (weak) IBOutlet NSImageView *background;
@property (weak) IBOutlet NSButton *addButton;
@property (weak) IBOutlet NSButton *deleteButton;
@property NSInteger numRules;
@end
